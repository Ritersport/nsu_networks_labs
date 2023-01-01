package ru.nsu.leorita.network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class SocketWrapper implements DeliveryChannel{

    private DatagramSocket socket;
    private int DATAGRAM_PACKET_LEN = 4096;

    public SocketWrapper() throws SocketException {
        socket = new DatagramSocket();
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
        socket.receive(receivePacket);
        return new RawMessage(receivePacket.getData(), receivePacket.getAddress(), receivePacket.getPort());
    }
}
