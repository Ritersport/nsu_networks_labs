package ru.nsu.leorita.network;

import java.io.IOException;
import java.net.InetAddress;

public interface MulticastDeliveryChannel {
    void broadcast(byte[] data) throws IOException;
    void close();
    InetAddress getLocalAddress();
    int getLocalPort();
}
