public class Main {
    public static void main(String args[]) {
        Market market = new Market("127.0.0.1", 5001);
        market.sendMessages();
    }
}
