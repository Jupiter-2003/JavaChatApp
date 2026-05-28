package server;

import common.Constants;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class ChatServer {

    //list of all connected clients
    public static ArrayList<ClientHandler> clients = new ArrayList<>();

    //rooms
    public static HashMap<String, ArrayList<ClientHandler>> rooms = new HashMap<>();



    public void startServer() {
        
        try {

            //default room
            rooms.put("general", new ArrayList<>());
            
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
