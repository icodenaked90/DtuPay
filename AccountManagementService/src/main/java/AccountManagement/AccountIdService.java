/*
@Author: Adin s164432
@Author: Jonathan s194134
@Author: Mila s223313
...
 */

package AccountManagement;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

import messaging.Event;
import messaging.MessageQueue;
import java.util.UUID;

public class AccountIdService {

    public static final String ACCOUNT_REGISTRATION_REQUESTED = "AccountRegistrationRequested";
    public static final String ACCOUNT_ID_ASSIGNED = "AccountIdAssigned";
    public static final String ACCOUNT_DEREGISTRATION_REQUESTED = "AccountDeregistrationRequested";
    public static final String ACCOUNT_DEREGISTRATION_COMPLETED = "AccountDeregistrationCompleted";
    private MessageQueue queue;
    private HashMap<String, String> userAccounts = new HashMap<String, String>();
    private Map<CorrelationId, CompletableFuture<Account>> correlations = new ConcurrentHashMap<>();


    public AccountIdService(MessageQueue q) {
        queue = q;
        queue.addHandler(ACCOUNT_REGISTRATION_REQUESTED, this::handleAccountRegistrationRequested);
        queue.addHandler(ACCOUNT_DEREGISTRATION_REQUESTED, this::handleAccountDeregistrationRequested);
    }

    public String register(Account a) {
        String id = generateId();
        userAccounts.put(id,a.bankAccount);
        return id;
    }

    public void handleAccountRegistrationRequested(Event e) {
        var a = e.getArgument(0, Account.class);
        var correlationid = e.getArgument(1, CorrelationId.class);

        Event event = new Event(ACCOUNT_ID_ASSIGNED, new Object[] { register(a), correlationid });
        queue.publish(event);
    }

    private String generateId(){
        while(true) {
            String uuid = UUID.randomUUID().toString();
            if (!userAccounts.containsKey(uuid)) return uuid;
        }
    }

    public void handleAccountDeregistrationRequested(Event e) {
        var id = e.getArgument(0, String.class);
        var correlationid = e.getArgument(1, CorrelationId.class);

        String errorMessage = "";
        if (userAccounts.containsKey(id)) {
            userAccounts.remove(id);
        } else {
            errorMessage = "User does not exist.";
        }
        Event event = new Event(ACCOUNT_DEREGISTRATION_COMPLETED, new Object[] { errorMessage, correlationid });
        queue.publish(event);
    }
}