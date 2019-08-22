public class Main {
    public static void main(String args[]) {
        Thread t1 = new Thread(new Runnable() {
            //            @Override
            public void run() {
                // code goes here.
                Router router1 = new Router(5000);

            }
        });
        t1.start();
        Thread t2 = new Thread(new Runnable() {
            //            @Override
            public void run() {
                // code goes here.
                Router router2 = new Router(5001);
            }
        });
        t2.start();
        System.out.print("Done\n");
    }
}
