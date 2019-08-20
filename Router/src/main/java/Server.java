// A Java program for a Server 

import java.net.*;
import java.io.*;

public class Server {
    //initialize socket and input stream 
    private Socket socket = null;
    private ServerSocket server = null;
    private DataInputStream in = null;

    // constructor with port 
    public Server(int port) {
        // starts server and waits for a connection 
        try {
            server = new ServerSocket(port);
            System.out.println("Server started");

            System.out.println("Waiting for a clients ...");
            while (true) {
                Client c = new Client(server.accept());
                Thread t = new Thread(c);
                t.start();
                System.out.println("Client accepted");
            }
            // takes input from the client socket
        } catch (IOException i) {
            System.out.println(i);
        }
    }
} 