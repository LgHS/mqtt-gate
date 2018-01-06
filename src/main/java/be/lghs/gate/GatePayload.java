package be.lghs.gate;

import java.nio.ByteBuffer;
import java.util.UUID;

public class GatePayload {

    public static GatePayload from(byte[] payload) {
        ByteBuffer buffer = ByteBuffer.wrap(payload);
        return new GatePayload(
            buffer.getLong(),
            buffer.getInt(),
            new UUID(buffer.getLong(), buffer.getLong()),
            buffer.getLong()
        );
    }

    private final long id;
    private final int version;
    private final UUID token;
    private final long pin;

    public GatePayload(long id, int version, UUID token, long pin) {
        this.id = id;
        this.version = version;
        this.token = token;
        this.pin = pin;
    }

    @Override
    public String toString() {
        return "GatePayload{" +
            "id=" + id +
            ", version=" + version +
            ", token=" + token +
            ", pin='" + pin + '\'' +
            '}';
    }
}
