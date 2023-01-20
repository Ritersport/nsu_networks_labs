package ru.nsu.leorita.network;

import java.io.IOException;
import java.net.InetAddress;

public interface DeliveryChannel {
    public void send(byte[] data, InetAddress receiverAddress, int receiverPort) throws IOException;

    public RawMessage receive() throws IOException;
    void close();

}
