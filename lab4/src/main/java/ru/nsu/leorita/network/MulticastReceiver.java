package ru.nsu.leorita.network;

import io.reactivex.rxjava3.core.BackpressureStrategy;
import io.reactivex.rxjava3.core.Flowable;
import ru.nsu.leorita.rdt.ReceivedMessage;
import ru.nsu.leorita.serializer.SnakesProto;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.nio.ByteBuffer;

public class MulticastReceiver {

    private final int DATAGRAM_PACKET_LEN = 4096;
    private InetAddress mcastAddress;
    private int mcastPort;
    private MulticastSocket socket;

    public MulticastReceiver(InetAddress mcastAddress, int port) throws IOException {

        this.mcastAddress = mcastAddress;
        this.mcastPort = port;
        socket = new MulticastSocket(9192);
        socket.joinGroup(mcastAddress);
    }

    public Flowable<ReceivedMessage> getMulticastFlowable() {
        return Flowable.create(emitter -> {
            while (!socket.isClosed()) {
                byte[] data = new byte[DATAGRAM_PACKET_LEN];
                DatagramPacket packet = new DatagramPacket(data, data.length);
                socket.receive(packet);
                ByteBuffer byteBuffer = ByteBuffer.allocate(packet.getLength());
                byteBuffer.put(packet.getData(), 0, packet.getLength());
                SnakesProto.GameMessage message = SnakesProto.GameMessage.parseFrom(byteBuffer.array());
                if (message != null) {
                    emitter.onNext(new ReceivedMessage(message, packet.getAddress(), packet.getPort()));
                }
            }
        }, BackpressureStrategy.BUFFER);
    }

    public void shutdown() {
        try {
            socket.leaveGroup(mcastAddress);
        } catch (IOException e) {
        }
        socket.close();
    }
}
