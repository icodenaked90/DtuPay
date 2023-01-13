package org.acme;

import lombok.Value;

import java.util.UUID;

@Value
public class CorrelationId {
    private UUID id;

    public static CorrelationId randomId() {
        return new CorrelationId(UUID.randomUUID());
    }
}