import java.io.*;
import java.net.*;

public class ChatClient {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 5000)) {
            // Thread to listen for messages from the server
            new Thread(new ListenThread(socket)).start();

            // Main thread handles outgoing messages
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader console = new BufferedReader(new InputStreamReader(System.in));

            String text;
            while ((text = console.readLine()) != null) {
                out.println(text);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class ListenThread implements Runnable {
    private BufferedReader in;

    public ListenThread(Socket socket) throws IOException {
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    @Override
    public void run() {
        try {
            String serverMsg;
            while ((serverMsg = in.readLine()) != null) {
                System.out.println("\n" + serverMsg);
            }
        } catch (IOException e) {
            System.out.println("Connection closed.");
        }
    }
}