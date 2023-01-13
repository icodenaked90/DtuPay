/* @Author: Mila (s223313)
   @Author: ...
   @Author: ...
   @Author: ...
 */

package AccountManagement;

import java.util.Map;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

import messaging.Event;
import messaging.MessageQueue;

public class AccountHandler {

    public static final String ACCOUNT_REGISTRATION_REQUESTED = "AccountRegistrationRequested";
    public static final String ACCOUNT_ID_ASSIGNED = "AccountIdAssigned";
    private MessageQueue queue;
    private Map<CorrelationId, CompletableFuture<Account>> correlations = new ConcurrentHashMap<>();

    public AccountHandler(MessageQueue q) {
        queue = q;
        queue.addHandler(ACCOUNT_REGISTRATION_REQUESTED, this::handleAccountRegistrationRequested);
        queue.addHandler(ACCOUNT_ID_ASSIGNED, this::handleAccountIdAssigned);
    }

    public Account register(Account a) {
        var correlationId = CorrelationId.randomId();
        correlations.put(correlationId, new CompletableFuture<>());
        Event event = new Event(ACCOUNT_REGISTRATION_REQUESTED, new Object[]{a, correlationId});
        queue.publish(event);
        return correlations.get(correlationId).join();
    }

    public void handleAccountRegistrationRequested(Event e) {
        var a = e.getArgument(0, Account.class);
        var eventCorrelationId = e.getArgument(1, CorrelationId.class);
        correlations.get(eventCorrelationId).complete(a);
    }

    public void handleAccountIdAssigned(Event e) {
        var eventCorrelationId = e.getArgument(1, CorrelationId.class);
    }
}