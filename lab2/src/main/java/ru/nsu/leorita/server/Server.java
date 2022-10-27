package ru.nsu.leorita.server;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static ru.nsu.leorita.Main.logger;

public class Server implements Runnable {
    private final ExecutorService threadPool = Executors.newCachedThreadPool();
    private final ServerSocket serverSocket;
    private final String SAVE_DIR = "./uploads/";

    public Server(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        new File(SAVE_DIR).mkdir();
    }

    @Override
    public void run() {
        int clientID = 1;
        while (!serverSocket.isClosed()) {
            try {
                Connection newConnection = new Connection(serverSocket.accept(), SAVE_DIR, clientID);
                threadPool.execute(() -> {
                    try {
                        newConnection.startTask();
                    } catch (IOException e) {
                        logger.error("Server: Connection.startTask() exception: " + e);
                    }
                });
            } catch (IOException e) {
                logger.error("Server: ServerSocket.accept() exception: " + e);
            }
            clientID++;
        }
    }

    public void shutdownServer() {
        threadPool.shutdown();
        try {
            serverSocket.close();
        } catch (IOException e) {
            logger.error("ServerSocket.close() exception: " + e);
        }
    }
}
