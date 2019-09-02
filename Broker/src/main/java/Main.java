import java.util.Scanner;

public class Main {


    public static void validate_input(String input) {

        String[] split_input;

        //split the input by spaces
        split_input = input.split(" ");

        if (!split_input[0].toLowerCase().equals("buy") || !split_input[0].toLowerCase().equals("sell"))
        {
            System.out.println("State whether you buying or selling");
        }

        for (int i = 0; i < split_input[2].length(); i++) {
            if(!Character.isDigit(split_input[2].charAt(i))) {
                System.out.println("Invalid Price");
            }
        }

        for (int i = 0; i < split_input[3].length(); i++) {
            if(!Character.isDigit(split_input[3].charAt(i))) {
                System.out.println("Invalid Quantity");
            }
        }

        for (int i = 0; i < split_input[4].length(); i++) {
            if(!Character.isDigit(split_input[4].charAt(i))) {
                System.out.println("Invalid Market ID");
            }
        }


    }

    public static void main(String args[]) {
        Broker broker = new Broker("127.0.0.1", 5000);
        broker.sendMessages();
    }
}
