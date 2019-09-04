import java.io.*;
import java.text.*;
import java.util.*;
import java.net.*;


public class Router {

    // Vector to store active clients
    static HashMap<Integer, ClientHandler> ar = new HashMap<Integer, ClientHandler>();
    static HashMap<Integer, ClientHandler> mr = new HashMap<Integer, ClientHandler>();

    // counter for Brokers
    static int i = 0;
    // counter for markets
    static int x = 0;

    public static void main(String[] args) throws IOException {

        Socket s = null;
        Socket m = null;

        marketMaker1 m1 = new marketMaker1();
        Thread mark = new Thread(m1);

        brokerMaker1 b1 = new brokerMaker1();
        Thread broker = new Thread(b1);

        broker.start();
        mark.start();


    }


    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static class marketMaker1 implements Runnable {

        ServerSocket ssMarket = new ServerSocket(5001, 5);
        private Socket m;
        private static int id = 100000;

        public marketMaker1() throws IOException {
            //  this.m = s;
        }

        public void run() {
            try {
                m = ssMarket.accept();
            } catch (IOException e) {
                e.printStackTrace();
            }

            DataInputStream dis = null;
            try {
                dis = new DataInputStream(m.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
            DataOutputStream dos = null;
            try {
                dos = new DataOutputStream(m.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Creating a new handler for this Market...");

            // Create a new handler object for handling this request.
            try {
                dos.writeUTF(Integer.toString(id));
            } catch (IOException e) {
                e.printStackTrace();
            }
            ClientHandler mtch = new ClientHandler(m, id, dis, dos);

            // Create a new Thread with this object.
            Thread t = new Thread(mtch);
            System.out.println("Adding this Market to active Market list");

            // add this client to active clients list
            mr.put(id, mtch);
            id++;
            t.start();
        }

    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static class brokerMaker1 implements Runnable {

        ServerSocket ss = new ServerSocket(5000, 5);
        private Socket s;
        private static int id = 500000;

        public brokerMaker1() throws IOException {
            // this.s = s;
        }

        public void run() {

            try {
                s = ss.accept();
            } catch (IOException e) {
                e.printStackTrace();
            }

            DataInputStream dis = null;
            try {
                dis = new DataInputStream(s.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
            DataOutputStream dos = null;
            try {
                dos = new DataOutputStream(s.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Creating a new handler for this Broker...");

            // Create a new handler object for handling this request.
            try {
                dos.writeUTF(Integer.toString(id));
            } catch (IOException e) {
                e.printStackTrace();
            }
            ClientHandler mtch = new ClientHandler(s, id, dis, dos);

            // Create a new Thread with this object.
            Thread t = new Thread(mtch);
            System.out.println("Adding this Broker to active Broker list");

            // add this client to active clients list
            ar.put(id, mtch);
            id++;
            t.start();

        }

    }


    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // ClientHandler class
    static class ClientHandler implements Runnable {
        Scanner scn = new Scanner(System.in);
        private int name;
        final DataInputStream dis;
        final DataOutputStream dos;
        Socket s;
        boolean isloggedin;

        // constructor
        public ClientHandler(Socket s, int name, DataInputStream dis, DataOutputStream dos) {
            this.dis = dis;
            this.dos = dos;
            this.name = name;
            this.s = s;
            this.isloggedin = true;
        }

        @Override
        public void run() {

            String received;
            while (true) {
                try {
                    // receive the string

                    received = dis.readUTF();

                    System.out.println(received);

                    if (received.equals("logout")) {
                        this.isloggedin = false;
                        this.s.close();
                        break;
                    }

                    // break the string into message and recipient part
                    boolean checksum = checksumEncryption(received);
                    if (checksum) {

                        StringTokenizer st = new StringTokenizer(received, " ");
                        String recipient = st.nextToken();
                        if (ar.containsKey(Integer.parseInt(recipient))) {
                            ar.get(Integer.parseInt(recipient)).dos.writeUTF(received);
                        } else if (mr.containsKey(Integer.parseInt(recipient))) {
                            mr.get(Integer.parseInt(recipient)).dos.writeUTF(received);
                        } else {
                            System.out.println("Invalid ID");
                        }
                    } else {
                        System.out.println("Invalid Checksum");
                    }
                } catch (IOException e) {

                    e.printStackTrace();
                }

            }
            try {
                // closing resources
                this.dis.close();
                this.dos.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private boolean checksumEncryption(String received) {
            return true;
        }
    }

    public int  GenerateCheckSum(String message, int length) {
        int sum = 0;
        int i;

        for (i = 0; i < length; i++) {
            int value = message.charAt(i);
            sum += value;
        }
        int checkSum = sum % 256;
        return(checkSum);
    }


    public boolean checkCheckSum(String message) {
        //String message1 = checkSumEncrypt(message);

        int checkSum = GenerateCheckSum(message, message.length());
        String[] splitMessage = message.split(" ");

       // String lastWord = message.substring(message.lastIndexOf(" ")+1);

        String removelastword = message.substring(message.lastIndexOf(" ") - 1);

        if (message.contains(Integer.toString(checkSum))) {
            return true;
        }
        return false;
    }
}