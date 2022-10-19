package ru.nsu.leorita.protocol;


import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;

public class OutputStreamWrapper {
    private final OutputStream outputStream;

    public OutputStreamWrapper(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public void write(int i) throws IOException {
        byte[] bytes = ByteBuffer.allocate(Integer.BYTES).putInt(i).array();
        outputStream.write(bytes);
    }

    public void write(byte[] b) throws IOException {
        write(b.length);
        outputStream.write(b);
    }
}
