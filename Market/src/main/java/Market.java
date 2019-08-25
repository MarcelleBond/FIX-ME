import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Market {
    private int id;
    // initialize socket and input output streams
    private Socket socket = null;
    private Scanner userInput = null;
    private DataInputStream serverInput = null;
    private DataOutputStream output = null;

    // constructor to put ip address and port
    public Market(String address, int port) {
        // establish a connection
        try {
            socket = new Socket(address, port);
            System.out.println("Connected");

            // takes input from terminal
            userInput = new Scanner(System.in);

            // gets input from the router
            serverInput = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            id = Integer.parseInt(serverInput.readUTF());
            System.out.print(id);
            // sends output to the router
            output = new DataOutputStream(socket.getOutputStream());
        } catch (UnknownHostException u) {
            System.out.println(u);
        } catch (IOException i) {
            System.out.println(i);
        }
    }


    public void sendMessages() {

        // string to read message from input
        String line = "";

        // keep reading until "Over" is input
        while (!line.equals("Over")) {
            try {
                line = userInput.nextLine();
                output.writeUTF(line);
            } catch (IOException i) {
                System.out.println(i);
            }
        }

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

