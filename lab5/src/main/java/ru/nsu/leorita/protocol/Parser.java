package ru.nsu.leorita.protocol;

import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class Parser {
    private static final byte WRONG_ADDRESS_TYPE = 0x08;

    private static final byte WRONG_COMMAND = 0x07;

    private static final int IPv4 = 0x01;

    private static final int DOMAIN_NAME = 0x03;

    private static final int CONNECT_COMMAND = 0x01;

    public static ConnectRequest parseConnect(ByteBuffer byteBuffer){
        try {
            byteBuffer.flip();
            ConnectRequest connect = new ConnectRequest();
            connect.setVersion(byteBuffer.get());
            connect.setnMethods(byteBuffer.get());
            byteBuffer.get(connect.getMethods());
            return connect;
        } catch (BufferUnderflowException exc){
            prepareBufferToWrite(byteBuffer);
            return null;
        }
    }

    public static Request parseRequest(ByteBuffer byteBuffer){
        try {
            Request request = new Request();
            byteBuffer.flip();
            request.setVersion(byteBuffer.get());

            byte command = byteBuffer.get();
            if (command != CONNECT_COMMAND) {
                request.setParseError(WRONG_COMMAND);
            }

            request.setCommand(command);
            byteBuffer.get();
            checkAddressType(byteBuffer.get(), byteBuffer, request);
            request.setTargetPort(byteBuffer.getShort());
            return request;
        } catch (BufferUnderflowException exc){
            prepareBufferToWrite(byteBuffer);
            return null;
        }
    }

    private static void prepareBufferToWrite(ByteBuffer byteBuffer){
        int newStartPos = byteBuffer.limit();
        byteBuffer.clear();
        byteBuffer.position(newStartPos);
    }

    private static void checkAddressType(byte addressType, ByteBuffer byteBuffer, Request request){
        request.setAddressType(addressType);

        switch (addressType){
            case IPv4:
                byteBuffer.get(request.getIp4Address());
                return;
            case DOMAIN_NAME:
                request.setDomainName(getDomainName(byteBuffer));
                return;
        }

        request.setParseError(WRONG_ADDRESS_TYPE);
    }

    private static String getDomainName(ByteBuffer byteBuffer){
        byte nameLength = byteBuffer.get();
        byte[] nameBytes = new byte[nameLength];
        byteBuffer.get(nameBytes);

        return new String(nameBytes, StandardCharsets.UTF_8);
    }
}
