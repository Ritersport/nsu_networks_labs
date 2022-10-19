package ru.nsu.leorita.client;

import com.google.protobuf.ByteString;
import ru.nsu.leorita.protocol.InputStreamWrapper;
import ru.nsu.leorita.protocol.OutputStreamWrapper;
import ru.nsu.leorita.protocol.PacketBuilder;
import ru.nsu.leorita.serializer.Packet;
import ru.nsu.leorita.serializer.PacketType;
import ru.nsu.leorita.settings.Constants;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static ru.nsu.leorita.Main.logger;

public class Client implements Runnable {
    private final File file;
    private final String checkSum;
    private final InetAddress serverIP;
    private final int serverPort;
    private Socket socket;
    private OutputStreamWrapper outputStreamWrapper;
    private InputStreamWrapper inputStreamWrapper;

    public Client(File file, String serverIP, int serverPort) throws IOException, NoSuchAlgorithmException {
        this.file = file;
        try {
            this.serverIP = InetAddress.getByName(serverIP);
        } catch (UnknownHostException e) {
            logger.error("Client: InetAddress.getByName() exception: " + e);
            throw e;
        }
        this.serverPort = serverPort;
        try {
            this.checkSum = PacketBuilder.getFileCheckSum(MessageDigest.getInstance(Constants.CHECKSUM_ALGORITHM), file);
        } catch (NoSuchAlgorithmException e) {
            logger.error("Client: MessageDigest.getInstance() exception: " + e);
            throw e;
        }
    }

    @Override
    public void run() {

        try {
            socket = new Socket(serverIP, serverPort);
        } catch (IOException e) {
            logger.error("Client: can't create socket: " + e);
            sessionTermination();
            return;
        }
        try {
            inputStreamWrapper = new InputStreamWrapper(socket.getInputStream());
        } catch (IOException e) {
            logger.error("Client: Socket.getInputStream() exception: " + e);
            sessionTermination();
            return;
        }
        try {
            outputStreamWrapper = new OutputStreamWrapper(socket.getOutputStream());
        } catch (IOException e) {
            logger.error("Client: Socket.getOutputStream() exception: " + e);
            sessionTermination();
            return;
        }
        if (!initializeConnection()) {
            logger.error("Client: can't initialize connection");
            sessionTermination();
            return;
        }
        System.out.println("Successful connection to server. Start sending file...");

        if (!sendFile()) {
            logger.error("Client: unsuccessful sendFile() operation");
            sessionTermination();
            return;
        }
        if (!getTransmissionResult()) {
            System.out.println("Unsuccessful transmission");
            logger.error("Client: unsuccessful file transmission");
            return;
        }
        sessionTermination();

    }

    private boolean initializeConnection() {
        Packet initPacket = PacketBuilder.buildInitPacket(file.getName(), file.length(), checkSum);
        try {
            sendPacket(initPacket);
        } catch (Exception e) {
            logger.error("Client: sendPacket() exception: " + e);
            return false;
        }
        try {
            Packet ackPacket = receivePacket();
            return ackPacket.getType() == PacketType.ACK;
        } catch (IOException e) {
            logger.error("Client: receivePacket() exception: " + e);
            return false;
        }
    }

    private boolean sendFile() {
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");

            try {
                while (randomAccessFile.getFilePointer() != randomAccessFile.length()) {
                    byte[] filePiece = new byte[Constants.MAX_PAYLOAD];
                    int bytesRead = randomAccessFile.read(filePiece);
                    try {
                        Packet filePacket = PacketBuilder.buildFilePacket(ByteString.copyFrom(ByteBuffer.allocate(bytesRead).put(filePiece, 0, bytesRead).array()));
                        try {
                            sendPacket(filePacket);
                        } catch (IOException e) {
                            logger.error("Server: sendFile() exception:" + e);
                            return false;
                        }
                    } catch (BufferOverflowException e) {
                        logger.error("Client: Can't allocate buffer for payload: " + e);
                        return false;
                    }
                }
            } catch (IOException e) {
                logger.error("Client: RandomAccessFile exception: " + e);
                return false;
            }
        } catch (FileNotFoundException e) {
            logger.error("Client: RandomAccessFile() exception: " + e);
            return false;
        }

        Packet endPacket = PacketBuilder.buildEndPacket();
        try {
            sendPacket(endPacket);
            return true;
        } catch (IOException e) {
            logger.error("Client: sendFile() exception:" + e);
            return false;
        }

    }

    private boolean getTransmissionResult() {
        try {
            Packet resultPacket = receivePacket();
            if (resultPacket.getType() == PacketType.FAIL) {
                logger.error("Client: File upload failed");
                System.out.println("The file was corrupted");
                return false;
            }
            if (resultPacket.getType() == PacketType.SUCCESS) {
                System.out.println("File successfully transferred");
                return true;
            }
            logger.error("Client: expected packet of type SUCCESS or FAIL, but received " + resultPacket.getType().name());
            return false;
        } catch (IOException e) {
            logger.error("Client: receiveFile() exception: " + e);
            return false;
        }
    }

    private void sendPacket(Packet packet) throws IOException {
        byte[] serializedPacket = packet.toByteArray();
        outputStreamWrapper.write(serializedPacket);
    }

    private Packet receivePacket() throws IOException {
        return Packet.parseFrom(inputStreamWrapper.readPacket());
    }

    private void sessionTermination() {
        if (!(socket == null)) {
            try {
                socket.close();
            } catch (IOException e) {
                logger.error("Client: Socket.close() exception: " + e);
            }
        } else {
            System.out.println("Server is dead xxx");
        }
    }
}
