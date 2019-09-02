import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class Broker {
    private int id;
    // initialize socket and input output streams
    private Socket socket = null;
    private Scanner userInput = null;
    private DataInputStream serverInput = null;
    private DataOutputStream output = null;


    public Broker(String address, int port) {
        try {
            socket = new Socket(address, port);
            System.out.println("Connected");

            // takes input from terminal
            userInput = new Scanner(System.in);

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

        // keep reading until "Over" is input
        while (!line.equals("Over")) {
            try {
                line = userInput.nextLine();
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

    public void reciveMessage() {
        String line = "";
        // reads message from client until "Over" is sent
        while (true) {
            try {
                line = serverInput.readUTF();
                System.out.println(line);

            } catch (IOException i) {
                System.out.println(i);
            }
        }
    }

    public void validate_input(String input)
    {
         String[] split_input;
        //buy or sell string to lower
        //
        //buy zar -2000 2 10001

        //instrument loop through instrument list to see if it exists
        split_input = input.trim().split(" ");
        if (split_input[0].toLowerCase() != "buy" || split_input[0].toLowerCase() != "sell")
        {
            System.out.println("State weather you are buying or selling niqqah");
        }

        for (int i = 0; i < split_input[1].length(); i++) {
            if(!Character.isDigit(split_input[1].charAt(i))) {
                System.out.println("Invalid Price");
            }
        }

        for (int i = 0; i < split_input[2].length(); i++) {
            if(!Character.isDigit(split_input[2].charAt(i))) {
                System.out.println("Invalid Quantity");
            }
        }

        for (int i = 0; i < split_input[2].length(); i++) {
            if(!Character.isDigit(split_input[2].charAt(i))) {
                System.out.println("Invalid Market ID");
            }
        }


    }
}