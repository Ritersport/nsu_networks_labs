package ru.nsu.leorita.protocol;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;


public class InputStreamWrapper {

    private final InputStream inputStream;
    private final int OFFSET = 0;

    public InputStreamWrapper(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public byte[] readPacket() throws IOException {

        byte[] packetLen = ByteBuffer.allocate(Integer.BYTES).array();
        int readBytes = inputStream.readNBytes(packetLen, OFFSET, Integer.BYTES);
        if (readBytes != Integer.BYTES) {
            throw new IOException();
        }

        int expectedPacketLenInt = ByteBuffer.allocate(Integer.BYTES).put(packetLen).rewind().getInt();
        byte[] serializedPacket = ByteBuffer.allocate(expectedPacketLenInt).array();
        int serializedPacketLen = inputStream.readNBytes(serializedPacket, OFFSET, expectedPacketLenInt);
        if (serializedPacketLen != expectedPacketLenInt) {
            throw new IOException();
        }
        return serializedPacket;
    }

}
