# This file is copied from the "Correlation Student Registration Example" zip file.
# Created by Hubert Baumeister.
# Accessed on 2023-01-11

package TokenManagement;

import java.util.UUID;

import lombok.Value;

@Value
public class CorrelationId {
    private UUID id;

    public static CorrelationId randomId() {
        return new CorrelationId(UUID.randomUUID());
    }
}