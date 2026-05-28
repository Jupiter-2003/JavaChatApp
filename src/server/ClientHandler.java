package server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler extends Thread{ //every client runs its own thread
    
    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;

    private String username;
    private String currentRoom;

    public ClientHandler(Socket socket){

        this.socket = socket;
        
        try {
            
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream())); //to read msg from client
            writer = new PrintWriter(socket.getOutputStream(), true); //to send message to client

            //asking username
            writer.println("Enter your username: ");

            //receiving username
            username = reader.readLine();

            //default room
            currentRoom = "general";

            //adding user to general room
            ChatServer.rooms.get(currentRoom).add(this);

            System.out.println(username + " connected.");

            //notifying everyone
            broadcastToRoom("SYSTEM: "+username+" joined "+currentRoom, currentRoom);

        } catch (Exception e) {
            e.printStackTrace();;
        }
    }

    @Override
    public void run() { //executes when clientHandler.start() is called
        //code executed by thread
        String message;

        try {
            
            while((message = reader.readLine()) != null){

                //exit command
                if(message.equalsIgnoreCase("/exit")){

                    ChatServer.clients.remove(this);
                    ChatServer.rooms.get(currentRoom).remove(this);

                    broadcastToRoom("SYSTEM: "+username+" left the room.", currentRoom);
                    socket.close();
                    break;
                }

                //check for command
                if(message.startsWith("/")){
                    handleCommand(message);
                }
                else{ //normal message

                    System.out.println("[" + currentRoom + "] " + username + ": " + message);
                    broadcastToRoom("[" + currentRoom + "] " + username + ": " + message, currentRoom);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //command handler; btw /exit is already used in the run() method
    public void handleCommand(String message){

        //help
        if(message.equalsIgnoreCase("/help")){ //triple quotes means text block

            writer.println("""
                Available commands:
                /help
                /list
                /whisper <username> <message>
                /join <room>
                /rooms
                /room
                /exit
                    """);
        
        }

        //list
        else if(message.equalsIgnoreCase("/list")){

            writer.println("Online users:");
            for(ClientHandler client : ChatServer.clients){
                writer.println(client.getUsername());
            }
        }

        //rooms
        else if(message.equalsIgnoreCase("/rooms")){

            writer.println("Available rooms: ");
            for(String room : ChatServer.rooms.keySet()){
                writer.println(room);
            }
        }

        //room
        else if(message.equalsIgnoreCase("/room")){

            writer.println("Current room: " + currentRoom);
        }

        //join
        else if(message.startsWith("/join")){
            String parts[] = message.split(" ", 2);

            if(parts.length<2){
                writer.println("Usage: /join <room>");
                return;
            }

            String newRoom = parts[1];

            joinRoom(newRoom);
        }

        //whisper
        else if(message.startsWith("/whisper")){

            String parts[] = message.split(" ");

            //invalid format
            if(parts.length < 3){
                writer.println("Usage: /whisper <username> <message>");
                return;
            }

            String targetUsername = parts[1];
            String privateMessage = "";
            for(int i = 2; i<parts.length; i++){
                privateMessage += parts[i] + " ";
            }

            sendPvtMsg(targetUsername, privateMessage);
        }

        //Unknown command
        else
            writer.println("Unknown command!");
    }

    //to join a room
    public void joinRoom(String newRoom){

        //removing from old room
        ChatServer.rooms.get(currentRoom).remove(this);

        //creating room if absent
        if(!ChatServer.rooms.containsKey(newRoom)){
            ChatServer.rooms.put(newRoom, new ArrayList<>());
        }

        //changing room
        currentRoom = newRoom;

        //adding to new room
        ChatServer.rooms.get(currentRoom).add(this);

        writer.println("Joined room: " + currentRoom);

        broadcastToRoom("SYSTEM: " + username + " joined the room.", currentRoom);
    }

    //private messaging
    public void sendPvtMsg(String targetUsername, String privateMessage){
        boolean found = false;

        for(ClientHandler client : ChatServer.clients){
            if(client.getUsername().equalsIgnoreCase(targetUsername)){

                client.writer.println("[Private] " + username + ": " + privateMessage);
                found = true;
                break;
            }
        }

        if(!found)
            writer.println("User not found.");
    }

    //room broadcast
    public void broadcastToRoom(String message, String room){

        ArrayList<ClientHandler> roomClients = ChatServer.rooms.get(room);

        for(ClientHandler client : roomClients) {
            if(client != this)
                client.writer.println(message); //prints message on the other clients' terminal
        }
    }

    //getting username as it is private
    public String getUsername(){
        return username;
    }
}
