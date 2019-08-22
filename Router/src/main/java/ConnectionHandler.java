import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ConnectionHandler implements Runnable {
    private Socket socket = null;
    private DataInputStream BrokerInput = null;
    private DataOutputStream output = null;

    public ConnectionHandler(Socket socket, int id) {

        try {
            this.socket = socket;
            BrokerInput = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            output = new DataOutputStream(socket.getOutputStream());
            output.writeUTF(String.valueOf(id));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void run() {
        try {
            String line = "";

            // reads message from client until "Over" is sent
            while (!line.equals("Over")) {
                try {
                    line = BrokerInput.readUTF();
                    System.out.println(line);

                } catch (IOException i) {
                    System.out.println(i);
                }
            }
            System.out.println("Closing connection");

            // close connection
            socket.close();
            BrokerInput.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
