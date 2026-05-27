package server;

import common.Constants;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer {

    public void startServer() {
        
        try {
            
            ServerSocket serverSocket = new ServerSocket(Constants.PORT);
            System.out.println("Server is listening on port: " + Constants.PORT);

            Socket socket = serverSocket.accept(); //program waits here till client tries to connect
            System.out.println("Client connected!");

            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream())); //to read msg from client
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true); //to send msg to client

            String message;

            while((message = reader.readLine()) != null){
                System.out.println("Client: " + message);
                if(message.equalsIgnoreCase("exit")){
                    System.out.println("Client disconnected.");
                    break;
                }
                writer.println("Message received.");
            }

            socket.close();
            serverSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
