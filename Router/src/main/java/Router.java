// A Java program for a Server
import java.net.*;
import java.io.*;

public class Router
{
    //initialize socket and input stream
    private Socket          socket   = null;
    private ServerSocket    server   = null;
    private DataInputStream in       =  null;

    // constructor with port
    public Router(int port)
    {
        // starts server and waits for a connection
        try
        {
            server = new ServerSocket(port);
//            System.out.println("Server started");

            System.out.println("Waiting for a Markets ...");

            while (true) {
                Client c = new Client(server.accept());
                Thread t = new Thread(c);
                t.start();
                System.out.println("Market accepted");
            }
        }
        catch(IOException i)
        {
            System.out.println(i);
        }
    }
}




//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.io.PrintStream;
//import java.net.ServerSocket;
//import java.net.Socket;
//import java.net.UnknownHostException;
//
//public class Router {
//    private ServerSocket RouterSocket;
//    private Socket AcceptSocket;
//    private BufferedReader Input;
//    private PrintStream output;
//
//    public static void main(String[] args) {
//        Router router = new Router();
//        System.out.println("Waiting for Client");
//        router.run();
//    }
//
//    public void run() {
//        try {
//            RouterSocket = new ServerSocket(9999);
//            AcceptSocket = RouterSocket.accept();
//            output = new PrintStream(AcceptSocket.getOutputStream());
//            output.println("Hello Client - from Server");
//            Input = new BufferedReader(new InputStreamReader(AcceptSocket.getInputStream()));
//            String line = "";
//        } catch (UnknownHostException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }
//}

