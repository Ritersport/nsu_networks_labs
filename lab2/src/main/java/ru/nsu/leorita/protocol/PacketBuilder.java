package ru.nsu.leorita.protocol;

import com.google.protobuf.ByteString;
import ru.nsu.leorita.serializer.Packet;
import ru.nsu.leorita.serializer.PacketType;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;

public class PacketBuilder {

    public static Packet buildInitPacket(String filename, long fileSize, String checksum) {
        return Packet.newBuilder().setType(PacketType.INIT)
                .setFilename(filename)
                .setChecksum(checksum)
                .setFilesize(fileSize).build();
    }

    public static Packet buildAckPacket() {
        return Packet.newBuilder().setType(PacketType.ACK).build();
    }

    public static Packet buildFilePacket(ByteString payload) {
        return Packet.newBuilder().
                setType(PacketType.DATA).
                setPayload(payload).build();
    }

    public static Packet buildEndPacket() {
        return Packet.newBuilder().setType(PacketType.END).build();
    }

    public static Packet buildFailPacket() {
        return Packet.newBuilder().setType(PacketType.FAIL).build();
    }

    public static Packet buildSuccessPacket() {
        return Packet.newBuilder().setType(PacketType.SUCCESS).build();
    }

    public static String getFileCheckSum(MessageDigest digest, File file) throws IOException {
        int BYTE_BUFFER_SIZE = 1024;
        int OFFSET = 0;
        FileInputStream fileInputStream = new FileInputStream(file);
        byte[] buffer = new byte[BYTE_BUFFER_SIZE];
        int bytesRead;
        while ((bytesRead = fileInputStream.read(buffer)) != -1) {
            digest.update(buffer, OFFSET, bytesRead);
        }
        fileInputStream.close();
        byte[] hash = digest.digest();
        StringBuilder sb = new StringBuilder();
        for (byte b : hash) {
            sb.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }
}
