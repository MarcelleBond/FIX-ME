public class Main {
    public static void main(String args[]) {
        Thread t1 = new Thread(new Runnable() {
            //            @Override
            public void run() {
                // code goes here.
                Server router = new Server(5000);
            }
        });
        t1.start();
        Thread t2 = new Thread(new Runnable() {
            //            @Override
            public void run() {
                // code goes here.
                Router router = new Router(5001);
            }
        });
        t2.start();
//        Server router = new Server(5000);
        System.out.print("Done");
    }
}
