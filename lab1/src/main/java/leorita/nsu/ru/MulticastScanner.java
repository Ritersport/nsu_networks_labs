package leorita.nsu.ru;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.*;

public class MulticastScanner {
    private final Map<UUID, Long> appIDs;
    private final MulticastSocket socket;
    private final byte[] content;
    private final InetAddress groupID;
    private final int packetLength;
    private final int RCV_TIMEOUT = 50;
    private final int IS_ALIVE_TIMEOUT = 1500;
    private final int PORT = 6088;


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
            System.err.println("Join group exception: " + e);
            interruptExecution();
        }

        DatagramPacket packet = new DatagramPacket(content, packetLength, groupID, PORT);
        while (!socket.isClosed()) {
            try {
                socket.send(packet);
            }
            catch (Exception e) {
                System.err.println("Socket.send() exception: " + e);
            }
            DatagramPacket rcvDatagram = new DatagramPacket(new byte[packetLength], packetLength, groupID, PORT);
            try {
                socket.setSoTimeout(RCV_TIMEOUT);
            } catch (Exception e) {
                System.err.println("Socket.setSoTimeout() exception: " + e);
            }
            try {
                socket.receive(rcvDatagram);
            }
            catch (Exception e) {
                System.err.println("Socket.receive() exception: " + e);
            }
            updateList(rcvDatagram);
        }
    }
    private void updateList(DatagramPacket rcv) {

        byte[] buf = new byte[packetLength];

        if (!Arrays.equals(buf, rcv.getData())) {
            UUID newUUID = UUIDMapper.fromBytes(rcv.getData());
            if (!appIDs.containsKey(newUUID)) {
                Long timeOfPut = System.currentTimeMillis();
                appIDs.put(newUUID, timeOfPut);
                printMap();
            }
            else {
                if (isInstanceIrrelevant(newUUID)) {
                    appIDs.replace(newUUID, System.currentTimeMillis());
                    printMap();
                }
            }
        }
        List<UUID> uuidToRemove = new ArrayList<>();
        for (Map.Entry<UUID, Long> instance: appIDs.entrySet()) {
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
        for (Map.Entry<UUID, Long> instance: appIDs.entrySet()) {
            long timeAlive = now - instance.getValue();
            System.out.println("UUID: " + instance.getKey().toString() + ", alive: " + timeAlive);
        }
        System.out.println("Total: " + appIDs.size());
    }

    public void interruptExecution() {
        try {
            socket.leaveGroup(groupID);
        } catch (IOException e) {
            System.err.println("Multicast socket leaving group exception: " + e);
        }
        socket.close();
        System.out.println("Socket is closed");
    }

}
