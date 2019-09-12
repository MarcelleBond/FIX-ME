
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Broker {

    private static ArrayList<String> List_of_instuments;
    private static ArrayList<String> IdList;



    public static String fix_notation(String message)
    {
        String[] split_input = message.split(" ");
        String x = null;
        if (split_input[1].equals("buy"))
        {
            x = "1";
        }
        else if (split_input[1].equals("sell"))
        {
            x = "2";
        }
        message = split_input[0] + " 35=D 54="+x+" 55="+split_input[2] + " 38="+split_input[3] + " 40=1 44="+split_input[4] + " " + GenerateCheckSum(message);
//        System.out.println("FIXED MESSAGE: " + message);
        return message;
    }

    public static String validate_input() {
        final Scanner scn = new Scanner(System.in);
        String final_string = "";
        List_of_instuments = new ArrayList<String>();
        List_of_instuments.add("zar");
        List_of_instuments.add("IBM");
        List_of_instuments.add("USD");
        List_of_instuments.add("wethinkcode");
        List_of_instuments.add("usdjpy");
        IdList = new ArrayList<String>();
        IdList.add("100000");
        IdList.add("100001");
        IdList.add("100002");
        IdList.add("100003");
        // Market id input
        while (true)
        {
            System.out.println("Enter Market ID you want to buy from?");
            String msg = scn.nextLine();
            if (IdList.contains(msg))
            {
                final_string += msg;
                break ;
            }
            System.out.println("Enter Valid Market ID");
        }
        // Buying or selling
        while (true)
        {
            System.out.println("Are you buying or selling?");
            String msg1 = scn.nextLine();
            if (msg1.toLowerCase().equals("buy") || msg1.toLowerCase().equals("sell"))
            {
                final_string += " " + msg1;
                break ;
            }
            System.out.println("State whether you buying or selling");
        }
        // Instrument list
        while (true)
        {
            System.out.println("Enter instrument you want to buy?");
            String msg2 = scn.nextLine();
            if (List_of_instuments.contains(msg2))
            {
                final_string += " " + msg2;
                break ;
            }
            System.out.println("Enter Valid instrument");
        }
        // Quantity
        while (true)
        {
            System.out.println("Enter Quantity?");
            int x = 1;
            String msg3 = scn.nextLine();
            for (int i = 0; i < msg3.length(); i++) {
                if (!Character.isDigit(msg3.charAt(i))) {
                    System.out.println("Invalid Quantity");
                    x = 0;
                }
            }
            if (x == 1)
            {
                final_string += " " + msg3;
                break ;
            }
            //System.out.println("Invalid Quantity");
        }
        // Price
        while (true)
        {
            System.out.println("Enter Price?");
            int x = 1;
            String msg4 = scn.nextLine();
            for (int i = 0; i < msg4.length(); i++) {
                if (!Character.isDigit(msg4.charAt(i))) {
                    System.out.println("Invalid Price");
                    x = 0;
                }
            }
            if (x == 1) {
                final_string += " " + msg4;
                break;
            }
            //System.out.println("Invalid Quantity");
        }
        return final_string;
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
                    //String msg = scn.nextLine();
                    String msg = validate_input();
                    msg = checkSumEncrypt(msg);
                    msg = fix_notation(msg);
                    try {
                        // write on the output stream
                        dos.writeUTF(id + " " + msg);
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
