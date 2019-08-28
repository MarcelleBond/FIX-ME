// A Java program for a Server

import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Router {
    //initialize socket and input stream
    private ServerSocket server = null;
    private HashMap<Integer, ConnectionHandler> connectionList = new HashMap<Integer, ConnectionHandler>();
    int id = 100000;

    // constructor with port
    public Router(int port) {
        // starts server and waits for a connection
        try {
            server = new ServerSocket(port,999999);
//            System.out.println("Server started");

            System.out.println("Waiting for a Connections ...");

            while (true) {
                ConnectionHandler c = new ConnectionHandler(server.accept(), id);
                connectionList.put(id, c);
                Thread t = new Thread(c);
                t.start();
                id++;
                System.out.println("Request accepted");
            }
        } catch (IOException i) {
            System.out.println(i);
        }
    }
}