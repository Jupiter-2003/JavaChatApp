package server;

import common.Constants;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ChatServer {

    //list of all connected clients
    public static ArrayList<ClientHandler> clients = new ArrayList<>();

    public void startServer() {
        
        try {
            
            ServerSocket serverSocket = new ServerSocket(Constants.PORT);
            System.out.println("Server is listening on port: " + Constants.PORT);

            while(true) {

                Socket socket = serverSocket.accept(); //program waits here till client tries to connect
                System.out.println("New client connected!");

                ClientHandler clientHandler = new ClientHandler(socket);
                clients.add(clientHandler);

                clientHandler.start(); //creates new thread
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
