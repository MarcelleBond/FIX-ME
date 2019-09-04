
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Broker {

    public static void validate_input(String input) {

        String[] split_input;

        //split the input by spaces
        split_input = input.split(" ");

        if (!split_input[0].toLowerCase().equals("buy") && !split_input[0].toLowerCase().equals("sell")) {
            System.out.println("State whether you buying or selling");
        }

        for (int i = 0; i < split_input[2].length(); i++) {
            if (!Character.isDigit(split_input[2].charAt(i))) {
                System.out.println("Invalid Price");
            }
        }

        for (int i = 0; i < split_input[3].length(); i++) {
            if (!Character.isDigit(split_input[3].charAt(i))) {
                System.out.println("Invalid Quantity");
            }
        }

        for (int i = 0; i < split_input[4].length(); i++) {
            if (!Character.isDigit(split_input[4].charAt(i))) {
                System.out.println("Invalid Market ID");
            }
        }
    }

    public static int GenerateCheckSum(String message) {
        int sum = 0;
        int length;
        int i;

        length = message.length();
        for (i = 0; i < length; i++) {
            int value = message.charAt(i);
            sum += value;
        }
        int checkSum = sum % 256;
        return (checkSum);
    }

    public static String checkSumEncrypt(String message) {
        int checkSum = GenerateCheckSum(message);
        return (message + " " + checkSum);
    }


    public static void main(String[] args) throws UnknownHostException, IOException {

        final Scanner scn = new Scanner(System.in);
        final int id;
        // getting localhost ip
        InetAddress ip = InetAddress.getByName("localhost");

        // establish the connection
        Socket s = new Socket(ip, 5000);

        // obtaining input and out streams
        final DataInputStream dis = new DataInputStream(s.getInputStream());
        id = Integer.parseInt(dis.readUTF());
        System.out.println(id);
        final DataOutputStream dos = new DataOutputStream(s.getOutputStream());

        // sendMessage thread
        Thread sendMessage = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {

                    // read the message to deliver.
                    String msg = scn.nextLine();
                    validate_input(msg);
                    String msg2 = checkSumEncrypt(msg);

                    try {
                        // write on the output stream
                        dos.writeUTF(id + " " + msg2);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        // readMessage thread
        Thread readMessage = new Thread(new Runnable() {
            @Override
            public void run() {

                while (true) {
                    try {
                        // read the message sent to this client
                        String msg = dis.readUTF();
                        System.out.println(msg);
                    } catch (IOException e) {

                        e.printStackTrace();
                    }
                }
            }
        });

        sendMessage.start();
        readMessage.start();
    }

}
