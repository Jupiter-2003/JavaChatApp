package client;

import common.Constants;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatClient {

    public void connect() {
        
        try {
            
            Socket socket = new Socket("localhost", Constants.PORT);
            System.out.println("Connected to server!");

            BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
            BufferedReader serverReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);

            String userInput, response;

            while(true){

                System.out.print("You: ");
                userInput = consoleReader.readLine();

                writer.println(userInput);

                if(userInput.equalsIgnoreCase("exit")){
                    System.out.println("Disconnected from server!");
                    break;
                }

                response = serverReader.readLine();
                System.out.println("Server: " + response);
            }

            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
