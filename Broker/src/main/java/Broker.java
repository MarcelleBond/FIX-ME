import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Broker {
    private int id;
    // initialize socket and input output streams
    private Socket socket = null;
    private DataInputStream userInput = null;
    private DataInputStream serverInput = null;
    private DataOutputStream output = null;


    public Broker(String address, int port) {
        try {
            socket = new Socket(address, port);
            System.out.println("Connected");

            // takes input from terminal
            userInput = new DataInputStream(System.in);

            // gets input from the router
            serverInput = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            id = Integer.parseInt(serverInput.readUTF());

            System.out.print(id);
            // sends output to the socket
            output = new DataOutputStream(socket.getOutputStream());
        } catch (
                UnknownHostException u) {
            System.out.println(u);
        } catch (
                IOException i) {
            System.out.println(i);
        }

    }

    public void sendMessages() {
        String line = "";
        System.out.print("FuckIt\n");

        // keep reading until "Over" is input
        while (!line.equals("Over")) {
            System.out.print("Im in\n");
            try {
                System.out.print("Im in try\n");
                line = userInput.readUTF();
                System.out.print(line);
                System.out.print("Im pass read\n");
                output.writeUTF(line);
            } catch (IOException i) {
                System.out.println(i);
            }
        }
        System.out.print("Im out\n");
        // close the connection
        try {
            userInput.close();
            serverInput.close();
            output.close();
            socket.close();
        } catch (IOException i) {
            System.out.println(i);
        }
    }
}