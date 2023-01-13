package AccountManagement;

import java.util.UUID;

import lombok.Value;

@Value
public class CorrelationID {
    private UUID id;

    public static CorrelationId randomId() {
        return new CorrelationId(UUID.randomUUID());
    }
}