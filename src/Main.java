import client.ChatClient;
import server.ChatServer;

public class Main {

    public static void main(String[] args) {

        ChatServer server = new ChatServer();
        server.startServer();

        ChatClient client = new ChatClient();
        client.connect();
    }
}
