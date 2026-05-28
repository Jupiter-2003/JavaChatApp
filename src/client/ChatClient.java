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

            BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in)); //to use to get input from user
            BufferedReader serverReader = new BufferedReader(new InputStreamReader(socket.getInputStream())); //to read msg from server
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true); //to send message to server

            //to receive username prompt
            System.out.println(serverReader.readLine());

            //to send username
            String username = consoleReader.readLine();
            writer.println(username);

            //thread to receive messages
            Thread receiveThread = new Thread(() -> {
                try {
                    
                    String serverMessage;

                    while((serverMessage = serverReader.readLine()) != null){

                        System.out.println(serverMessage);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            receiveThread.start();

            //sending messages
            String userInput;

            while(true){

                userInput = consoleReader.readLine();

                writer.println(userInput);

                if(userInput.equalsIgnoreCase("exit")){ //exit condition

                    socket.close();
                    break;
                }
            }

            socket.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
