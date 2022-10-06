package leorita.nsu.ru;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class MulticastScanner {

    final static Logger logger = Logger.getLogger(MulticastScanner.class);

    private final Map<UUID, Long> appIDs;
    private final MulticastSocket socket;
    private final byte[] content;
    private final InetAddress groupID;
    private final int packetLength;
    private final int RCV_TIMEOUT = 50;
    private final int IS_ALIVE_TIMEOUT = 1500;
    private final int PORT = 6088;
    private Thread rcvTread;

    public MulticastScanner(Application app) throws IOException {
        groupID = app.getGroupID();
        appIDs = new HashMap<>();
        socket = new MulticastSocket(PORT);
        content = UUIDMapper.toBytes(app.getAppID());
        packetLength = content.length;
    }

    public void scanNetwork() {
        try {
            socket.joinGroup(groupID);
        } catch (IOException e) {
            logger.error("Join group exception: " + e);
            interruptExecution();
        }

        startReceiveThread();

        DatagramPacket packet = new DatagramPacket(content, packetLength, groupID, PORT);

        while (!socket.isClosed()) {
            try {
                socket.send(packet);
            } catch (Exception e) {
                logger.error("Socket.send() exception: " + e);
            }
        }
    }

    private void updateList(DatagramPacket rcv) {

        if (!isEmpty(rcv.getData(), packetLength)) {
            UUID newUUID = UUIDMapper.fromBytes(rcv.getData());

            if (!appIDs.containsKey(newUUID)) {
                Long timeOfPut = System.currentTimeMillis();
                appIDs.put(newUUID, timeOfPut);
                printMap();
            } else {
                if (isInstanceIrrelevant(newUUID)) {
                    appIDs.replace(newUUID, System.currentTimeMillis());
                    printMap();
                }
            }
        }
        List<UUID> uuidToRemove = new ArrayList<>();
        for (Map.Entry<UUID, Long> instance : appIDs.entrySet()) {
            if (isInstanceIrrelevant(instance.getKey())) {
                uuidToRemove.add(instance.getKey());
            }
        }
        if (uuidToRemove.size() > 0) {
            uuidToRemove.forEach((appIDs::remove));
            printMap();
        }
    }

    private boolean isInstanceIrrelevant(UUID uuid) {
        long timeAlive = System.currentTimeMillis() - appIDs.get(uuid);
        return timeAlive > IS_ALIVE_TIMEOUT;
    }

    private void printMap() {
        String clearOutput = "\033[H\033[2J";
        System.out.print(clearOutput);
        Long now = System.currentTimeMillis();
        for (Map.Entry<UUID, Long> instance : appIDs.entrySet()) {
            long timeAlive = now - instance.getValue();
            System.out.println("UUID: " + instance.getKey().toString() + ", alive: " + timeAlive);
        }
        System.out.println("Total: " + appIDs.size());
    }

    private void startReceiveThread() {
        rcvTread = new Thread(() -> {
            while (!socket.isClosed()) {
                DatagramPacket rcvDatagram = new DatagramPacket(new byte[packetLength], packetLength, groupID, PORT);
                try {
                    socket.setSoTimeout(RCV_TIMEOUT);
                } catch (Exception e) {
                    logger.error("Socket.setSoTimeout() exception: " + e);
                }
                try {
                    socket.receive(rcvDatagram);
                } catch (Exception e) {
                    logger.error("Socket.receive() exception: " + e);
                }
                updateList(rcvDatagram);
            }

        });

        rcvTread.start();
    }

    public void interruptExecution() {
        try {
            socket.leaveGroup(groupID);
        } catch (IOException e) {
            logger.error("MulticastSocket.leaveGroup threw an exception: " + e);
        }
        socket.close();
        try {
            rcvTread.join();
        } catch (InterruptedException e) {
            logger.error("Thread.join() threw an exception: " + e);
        }
        logger.info("Socket is closed");
    }

    private boolean isEmpty(byte[] data, int len) {
        for (int i = 0; i < len; i++) {
            if (data[i] != 0) {
                return false;
            }
        }
        return true;
    }
}
