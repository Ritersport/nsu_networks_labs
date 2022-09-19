package leorita.nsu.ru;

import java.nio.ByteBuffer;
import java.util.UUID;

public class UUIDMapper {

    public static byte[] toBytes(UUID uuid) {
        ByteBuffer buf = ByteBuffer.wrap(new byte[16]);
        buf.putLong(uuid.getMostSignificantBits());
        buf.putLong(uuid.getLeastSignificantBits());
        return buf.array();
    }

    public static UUID fromBytes(byte[] bytes) {
        ByteBuffer buf = ByteBuffer.wrap(bytes);
        long high = buf.getLong();
        long low = buf.getLong();
        return new UUID(high, low);
    }
}
