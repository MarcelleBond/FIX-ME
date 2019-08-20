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

