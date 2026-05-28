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

                if(message.equalsIgnoreCase("exit")){

                    ChatServer.clients.remove(this);
                    broadcastMessage("SYSTEM: "+username+" has left the chat.");
                    socket.close();
                    break;
                }

                System.out.println(username + ": " + message);
                broadcastMessage(username + ": " + message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //to send message to all clients
    public void broadcastMessage(String message){

        for(ClientHandler client : ChatServer.clients) {
            if(client != this)
                client.writer.println(message);
        }
    }
}
