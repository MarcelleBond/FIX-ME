public class Main {
    public static void main(String args[])
    {
        Broker broker = new Broker("127.0.0.1", 5000);
        broker.sendMessages();
    }
}
