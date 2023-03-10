//	@Author: Jonathan (s194134)
package TestStubs;

import Payment.models.CorrelationId;
import Payment.models.Token;
import messaging.Event;
import messaging.MessageQueue;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class StubTokenService {
    // @Author: Jonathan (s194134)
    // This test stub simulates a very simple version of token service.
    // It only contains the things we need Payment service
    public static final String TOKEN_VALIDATION_REQUESTED = "TokenValidationRequested";
    public static final String TOKEN_VALIDATION_COMPLETED = "TokenValidationCompleted";

    private Map<Token, String> tokens = new ConcurrentHashMap<>();

    private MessageQueue queue;

    public StubTokenService(MessageQueue q) {
        queue = q;
        queue.addHandler(TOKEN_VALIDATION_REQUESTED, this::handleTokenValidationRequested);
    }

    public void addToken(Token token, String cid) {
        tokens.put(token, cid);
    }

    // @Author: Jonathan (s194134)
    private void handleTokenValidationRequested(Event e) {
        var token = e.getArgument(0, Token.class);
        var eventCorrelationId = e.getArgument(1, CorrelationId.class);
        if (tokens.containsKey(token)) {
            String cid = tokens.get(token);
            e = new Event(TOKEN_VALIDATION_COMPLETED, new Object[]{cid, eventCorrelationId});
        } else {
            e = new Event(TOKEN_VALIDATION_COMPLETED, new Object[]{"", eventCorrelationId});
        }
        queue.publish(e);
    }
}