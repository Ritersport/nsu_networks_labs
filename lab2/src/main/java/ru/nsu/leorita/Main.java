package ru.nsu.leorita;

import org.apache.log4j.Logger;
import picocli.CommandLine;
import ru.nsu.leorita.cli.Application;

public class Main {

    public static Logger logger = Logger.getLogger(Main.class);
    public static void main(String[] args) {
        Application app = new Application();
        CommandLine cl = new CommandLine(app);
        System.exit(cl.execute(args));
    }
}