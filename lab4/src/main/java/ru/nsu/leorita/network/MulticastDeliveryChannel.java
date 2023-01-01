package ru.nsu.leorita.network;

import java.io.IOException;

public interface MulticastDeliveryChannel {
    public void broadcast(byte[] data) throws IOException;
    public RawMessage receive() throws IOException;

}
