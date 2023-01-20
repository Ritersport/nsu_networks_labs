package ru.nsu.leorita.network;

import ru.nsu.leorita.rdt.ReceivedMessage;
import ru.nsu.leorita.rdt.TransferPublisher;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.ArrayList;

public class MulticastSocketWrapper implements MulticastDeliveryChannel, TransferPublisher {
    private final MulticastSocket multicastSocket;
    private ArrayList<Subscriber> subscribers;
    private InetAddress multicastGroup;

    private int DATAGRAM_PACKET_LEN = 4096;

    public MulticastSocketWrapper(InetAddress ip) throws IOException {
        multicastGroup = ip;
        multicastSocket = new MulticastSocket();
        multicastSocket.joinGroup(multicastGroup);
        subscribers = new ArrayList<>();
    }

    @Override
    public void broadcast(byte[] data) throws IOException {
        DatagramPacket sendPacket = new DatagramPacket(data, data.length, multicastGroup, 9192);
        multicastSocket.send(sendPacket);
    }



    @Override
    public void close() {
        multicastSocket.close();
    }

    @Override
    public void notifySubscribers(ReceivedMessage message) {
        subscribers.forEach(subscriber -> subscriber.update(message));
    }

    @Override
    public void addSubscriber(Subscriber subscriber) {
        subscribers.add(subscriber);
    }

    @Override
    public InetAddress getLocalAddress() {
        return multicastSocket.getLocalAddress();
    }

    @Override
    public int getLocalPort() {
        return multicastSocket.getLocalPort();
    }
}
