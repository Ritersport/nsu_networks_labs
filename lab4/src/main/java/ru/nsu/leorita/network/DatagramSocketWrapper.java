package ru.nsu.leorita.network;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;

public class DatagramSocketWrapper implements DeliveryChannel {

    private final int DATAGRAM_PACKET_LEN = 4096;
    private DatagramSocket socket;
    private Logger logger = Logger.getLogger(DatagramSocketWrapper.class);

    public DatagramSocketWrapper(int rcvTimeout) throws SocketException {
        try {

            socket = new DatagramSocket();
            socket.setSoTimeout(rcvTimeout);
        } catch (SocketException e) {
            logger.error("DatagramSocketWrapper constructor:" + e);
            throw e;
        }
    }

    @Override
    public void send(byte[] data, InetAddress receiverAddress, int receiverPort) throws IOException {
        DatagramPacket sendPacket = new DatagramPacket(data, data.length, receiverAddress, receiverPort);
        socket.send(sendPacket);
    }

    @Override
    public RawMessage receive() throws IOException {
        byte[] data = new byte[DATAGRAM_PACKET_LEN];
        DatagramPacket receivePacket = new DatagramPacket(data, data.length);
        try {
            socket.receive(receivePacket);
        } catch (SocketTimeoutException e) {
            return null;
        }
        ByteBuffer buffer = ByteBuffer.allocate(receivePacket.getLength());
        buffer.put(receivePacket.getData(), 0, receivePacket.getLength());
        return new RawMessage(buffer.array(), receivePacket.getAddress(), receivePacket.getPort());
    }

    @Override
    public void close() {
        socket.close();
    }
}
