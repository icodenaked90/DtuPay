package studentid.service;

import java.util.UUID;

import lombok.Value;

@Value
public class CorrelationId {
	private UUID id;

	public static CorrelationId randomId() {
		return new CorrelationId(UUID.randomUUID());
	}
}
