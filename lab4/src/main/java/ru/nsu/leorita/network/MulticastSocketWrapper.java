package ru.nsu.leorita.network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class MulticastSocketWrapper implements MulticastDeliveryChannel{
    private MulticastSocket multicastSocket;
    private InetAddress multicastGroup;
    private int port;
    private int DATAGRAM_PACKET_LEN = 4096;

    public MulticastSocketWrapper() throws IOException {
        multicastGroup = InetAddress.getByName("239.192.0.4");
        port = 9192;
        multicastSocket = new MulticastSocket(port);
        multicastSocket.joinGroup(multicastGroup);
    }

    @Override
    public void broadcast(byte[] data) throws IOException {
        DatagramPacket sendPacket = new DatagramPacket(data, data.length);
        multicastSocket.send(sendPacket);
    }
    @Override
    public RawMessage receive() throws IOException {
        byte[] data = new byte[DATAGRAM_PACKET_LEN];
        DatagramPacket receivePacket = new DatagramPacket(data, data.length);
        multicastSocket.receive(receivePacket);
        return new RawMessage(receivePacket.getData(), receivePacket.getAddress(), receivePacket.getPort());
    }
}
