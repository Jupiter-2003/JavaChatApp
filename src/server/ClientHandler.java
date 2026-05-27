package server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler extends Thread{
    
    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;

    public ClientHandler(Socket socket){

        this.socket = socket;
        
        try {
            
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream())); //to read msg from client
            writer = new PrintWriter(socket.getOutputStream(), true); //to send message to client

        } catch (Exception e) {
            e.printStackTrace();;
        }
    }

    @Override
    public void run() {

        String message;

        try {
            
            while((message = reader.readLine()) != null){
                System.out.println("Client says: " + message);

                broadcastMessage(message);

                if(message.equalsIgnoreCase("exit")){
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //to send message to all clients
    public void broadcastMessage(String message){

        for(ClientHandler client : ChatServer.clients) {
            client.writer.println(message);
        }
    }
}
