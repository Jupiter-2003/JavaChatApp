package server;

import common.Constants;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer {

    public void startServer() {
        
        try {
            
            ServerSocket serverSocket = new ServerSocket(Constants.PORT);
            System.out.println("Server is listening on port: " + Constants.PORT);

            Socket socket = serverSocket.accept(); //program waits here till client tries to connect
            System.out.println("Client connected!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
