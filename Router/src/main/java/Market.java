import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Market implements Runnable {
    private Socket socket = null;
    private ServerSocket server = null;
    private DataInputStream in = null;

    public void run() {
        try {
            in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            String line = "";

            // reads message from client until "Over" is sent
            while (!line.equals("Over")) {
                try {
                    line = in.readUTF();
                    System.out.println(line);

                } catch (IOException i) {
                    System.out.println(i);
                }
            }
            System.out.println("Closing connection");

            // close connection
            socket.close();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
