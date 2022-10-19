package ru.nsu.leorita.cli;

import picocli.CommandLine;
import ru.nsu.leorita.client.Client;
import ru.nsu.leorita.server.Server;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.Callable;

import static ru.nsu.leorita.Main.logger;

@CommandLine.Command(name = "file-transmitter", description = {"Custom file transmitter"})
public class Application implements Callable<Integer> {

    @CommandLine.Option(names = {"-f", "--file"}, paramLabel = "FILE", description = {"Upload file to server"})
    File file;

    @CommandLine.Option(names = {"-r", "--receive"}, description = {"If the flag is set, run as a server, else run as a client"})
    boolean receive = false;

    @CommandLine.Option(names = {"-p", "--port"}, description = {"Server port"})
    int port = 8068;

    @CommandLine.Option(names = {"-a", "--address"}, description = "Server address (option for client)")
    String address = "127.0.0.1";

    @CommandLine.Option(names = {"-h", "--help"}, usageHelp = true, description = {"Print help"})
    boolean help = false;

    @Override
    public Integer call() throws Exception {
        if (receive) {
            try {
                Server server = new Server(port);
                Runtime.getRuntime().addShutdownHook(new Thread(server::shutdownServer));
                server.run();
            } catch (IOException e) {
                logger.error("Unable to start server: " + e);
                return 0;
            }
        } else {
            if (file == null) {
                System.out.println("No file passed");
                return 0;
            }
            if (!file.isFile() || !file.exists() || file.canExecute()) {
                System.out.println("Invalid file or file does not exist");
                return 0;
            }

            try {
                Client client = new Client(file, address, port);
                client.run();
            } catch (IOException e) {
                logger.error("Unable to start client: " + e);
                return 0;
            }

        }
        return 0;
    }
}
