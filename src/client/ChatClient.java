package client;

import common.Constants;
import java.net.Socket;

public class ChatClient {

    public void connect() {
        
        try {
            
            Socket socket = new Socket("localhost", Constants.PORT);
            System.out.println("Connected to server!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
