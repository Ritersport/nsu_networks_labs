package ru.nsu.leorita.server;

import ru.nsu.leorita.protocol.InputStreamWrapper;
import ru.nsu.leorita.protocol.OutputStreamWrapper;
import ru.nsu.leorita.protocol.PacketBuilder;
import ru.nsu.leorita.serializer.Packet;
import ru.nsu.leorita.serializer.PacketType;

import java.io.*;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static ru.nsu.leorita.Main.logger;

public class Connection {

    private final Socket socket;
    private final String saveDir;
    private ScheduledExecutorService timer;
    private long lastLen;
    private int tacts = 0;
    private final int ID;
    private File file;
    private OutputStreamWrapper outputStreamWrapper;
    private InputStreamWrapper inputStreamWrapper;
    private String fileName;
    private String fileExtension;
    private String expectedChecksum;
    private long expectedFilesize;

    public Connection(Socket socket, String saveDir, int ID) {
        this.socket = socket;
        this.saveDir = saveDir;
        this.ID = ID;
        this.timer = Executors.newScheduledThreadPool(1);
    }

    public void startTask() throws IOException {
        outputStreamWrapper = new OutputStreamWrapper(socket.getOutputStream());
        inputStreamWrapper = new InputStreamWrapper(socket.getInputStream());
        if (!acceptConnection()) {
            logger.error("Server: no INIT packets from Client");
            terminateTask();
            return;
        }
        if (!receiveFile()) {
            logger.error("Server: Connection.receiveFile() returned 'false'");
        }
        terminateTask();
    }

    private boolean acceptConnection() throws IOException {

        Packet initPacket = Packet.parseFrom(inputStreamWrapper.readPacket());
        if (initPacket.getType() != PacketType.INIT) {
            logger.error("Server: expected INIT packet");
            terminateTask();
            return false;
        }
        expectedChecksum = initPacket.getChecksum();
        expectedFilesize = initPacket.getFilesize();
        parseFilename(initPacket.getFilename());
        Packet ackPacket = PacketBuilder.buildAckPacket();
        outputStreamWrapper.write(ackPacket.toByteArray());
        return true;
    }

    private void parseFilename(String fullName) {
        int dotPos = fullName.indexOf('.');
        fileName = fullName.substring(0, dotPos - 1);
        fileExtension = fullName.substring(dotPos);
    }

    private boolean receiveFile() throws IOException {

        file = new File(saveDir + "/" + fileName + fileExtension);
        if (file.exists()) {
            int counter = 1;
            do {
                file = new File(saveDir + "/" + fileName + "(" + counter + ")" + fileExtension);
                counter++;
            } while (file.exists());
        }
        timer.scheduleAtFixedRate(this::countInstantSpeed, 0, 3000, TimeUnit.MILLISECONDS);
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
            Packet filePacket;
            do {
                filePacket = Packet.parseFrom(inputStreamWrapper.readPacket());
                if (filePacket.getType() != PacketType.DATA) {
                    if (filePacket.getType() != PacketType.END) {
                        logger.error("Server: expected packet of type DATA");
                        return false;
                    }
                    break;
                }
                byte[] payload = filePacket.getPayload().toByteArray();
                randomAccessFile.write(payload);
            } while (filePacket.getType() != PacketType.END);


            if (!checkReceivedFile()) {
                Packet failurePacket = PacketBuilder.buildFailPacket();
                outputStreamWrapper.write(failurePacket.toByteArray());
                System.out.println("Client " + ID + ": the file was not received correctly");
                logger.error("Server: the file was not received correctly");
                terminateTask();
                return false;
            }
            countInstantSpeed();
            countAverageSpeed();
            timer.shutdown();
            Packet successPacket = PacketBuilder.buildSuccessPacket();
            outputStreamWrapper.write(successPacket.toByteArray());
            return true;
        } catch (FileNotFoundException e) {
            logger.error("Server: RandomAccessFile() exception: " + e);
            return false;
        }
    }

    private boolean checkReceivedFile() {
        try {
            String checkSum = PacketBuilder.getFileCheckSum(MessageDigest.getInstance("MD5"), file);
            long fileSize = file.length();
            return (checkSum.equals(expectedChecksum)) && (fileSize == expectedFilesize);
        } catch (NoSuchAlgorithmException e) {
            logger.error("Server: MessageDigest.Instance() exception: " + e);
            return false;
        } catch (IOException e) {
            logger.error("getFileCheckSum() exception: " + e);
            return false;
        }
    }

    private Runnable countInstantSpeed() {
        if (!socket.isClosed()) {
            tacts++;
            long actualLen = file.length();
            System.out.println("Data transfer rate for ID " + ID + ": " + (actualLen - lastLen) + " bytes/sec");
            lastLen = actualLen;
        } else {
            terminateTask();
        }
        return null;
    }

    private void countAverageSpeed() {
        System.out.println("Average speed for ID " + ID + ": " + (file.length() / tacts) + " bytes/sec");
    }

    private void terminateTask() {
        System.out.println("Session of client " + ID + " is ended");
        try {

            socket.close();
        } catch (IOException e) {
            logger.error("Socket.close() exception: " + e);
        }
        timer.shutdown();
    }
}
