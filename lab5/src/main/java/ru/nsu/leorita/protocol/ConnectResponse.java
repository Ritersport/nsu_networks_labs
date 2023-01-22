package ru.nsu.leorita.protocol;

public class ConnectResponse {
    private byte version = 0x05;

    private byte method = 0x00;

    public void setMethod(byte method) {
        this.method = method;
    }

    public byte[] toBytes(){
        return new byte[]{version, method};
    }
}
