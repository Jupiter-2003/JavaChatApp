package server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler extends Thread{ //every client runs its own thread
    
    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;

    private String username;

    public ClientHandler(Socket socket){

        this.socket = socket;
        
        try {
            
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream())); //to read msg from client
            writer = new PrintWriter(socket.getOutputStream(), true); //to send message to client

            //asking username
            writer.println("Enter your username: ");

            //receiving username
            username = reader.readLine();

            System.out.println(username + " connected.");

            //notifying everyone
            broadcastMessage("SYSTEM: "+username+" joined the chat!");

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
                    broadcastMessage("SYSTEM: "+username+" has left the chat.");
                    socket.close();
                    break;
                }

                //check for command
                if(message.startsWith("/")){
                    handleCommand(message);
                }
                else{

                    System.out.println(username + ": " + message);
                    broadcastMessage(username + ": " + message);
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

        //wisper
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

        //Unkown command
        else
            writer.println("Unknown command!");
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


    //to broadcast message to all clients
    public void broadcastMessage(String message){

        for(ClientHandler client : ChatServer.clients) {
            if(client != this)
                client.writer.println(message); //prints message on the other clients' terminal
        }
    }

    //getting username as username is private
    public String getUsername(){
        return username;
    }
}
