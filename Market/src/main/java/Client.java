//import java.io.*;
//import java.net.Socket;
//import java.net.UnknownHostException;
//import java.util.Scanner;
//
//public class Client {
//    private Socket clientSocket;
//    private BufferedReader Input;
//    private PrintStream output;
//    private Socket socket = null;
////        private DataInputStream input   = null;
////        private DataOutputStream out     = null;
//
//    public static void main(String[] args) throws IOException {
//        Client client = new Client();
//        client.run();
//    }
//
//    public void run() throws IOException {
//        Scanner scan = new Scanner(System.in);
//        String text;
//        try {
//            clientSocket = new Socket("localhost", 9999);
//            output = new PrintStream(clientSocket.getOutputStream());
////            output.println(text);
//            Input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
//            String message = Input.readLine();
//            System.out.println(message);
//        } catch (
//                UnknownHostException e) {
//            e.printStackTrace();
//        } catch (
//                IOException e) {
//            e.printStackTrace();
//        }
//    }
//}


// A Java program for a Client
import java.net.*;
import java.io.*;

public class Client
{
    // initialize socket and input output streams
    private Socket socket            = null;
    private DataInputStream  input   = null;
    private DataOutputStream out     = null;

    // constructor to put ip address and port
    public Client(String address, int port)
    {
        // establish a connection
        try
        {
            socket = new Socket(address, port);
            System.out.println("Connected");

            // takes input from terminal
            input  = new DataInputStream(System.in);

            // sends output to the socket
            out    = new DataOutputStream(socket.getOutputStream());
        }
        catch(UnknownHostException u)
        {
            System.out.println(u);
        }
        catch(IOException i)
        {
            System.out.println(i);
        }

        // string to read message from input
        String line = "";

        // keep reading until "Over" is input
        while (!line.equals("Over"))
        {
            try
            {
                line = input.readLine();
                out.writeUTF(line);
            }
            catch(IOException i)
            {
                System.out.println(i);
            }
        }

        // close the connection
        try
        {
            input.close();
            out.close();
            socket.close();
        }
        catch(IOException i)
        {
            System.out.println(i);
        }
    }

    public static void main(String args[])
    {
        Client client = new Client("127.0.0.1", 5000);
    }
}