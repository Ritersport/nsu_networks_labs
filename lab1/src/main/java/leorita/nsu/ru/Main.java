package leorita.nsu.ru;



import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class Main {

    public static void main(String[] args) throws IOException {

        if (args.length == 0) {
            System.out.println("Enter group IP as an argument");
            return;
        }
        InetAddress groupID;
        try {
            groupID = InetAddress.getByName(args[0]);
        } catch (UnknownHostException e) {
            System.out.println("Enter group IP as an argument");
            return;
        }

        Application app = Application.getInstance(groupID);
        MulticastScanner scanner = new MulticastScanner(app);
        Runtime.getRuntime().addShutdownHook(new Thread(scanner::interruptExecution));

        scanner.scanNetwork();
    }
}
