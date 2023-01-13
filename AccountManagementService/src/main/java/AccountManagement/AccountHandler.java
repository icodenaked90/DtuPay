package AccountManagement;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

import messaging.Event;
import messaging.MessageQueue;
import java.util.UUID;

public class AccountHandler {

    public static final String ACCOUNT_REGISTRATION_REQUESTED = "AccountRegistrationRequested";
    public static final String ACCOUNT_ID_ASSIGNED = "AccountIdAssigned";
    private MessageQueue queue;
    private HashMap<String, String> userAccounts = new HashMap<String, String>();
    private Map<CorrelationId, CompletableFuture<Account>> correlations = new ConcurrentHashMap<>();

    public AccountHandler(MessageQueue q) {
        queue = q;
        queue.addHandler(ACCOUNT_REGISTRATION_REQUESTED, this::handleAccountRegistrationRequested);
    }

    public String register(Account a) {
        String id = generateId();
        userAccounts.put(id,bankAccount);
        return id;
    }

    public void handleAccountRegistrationRequested(Event e) {
        var a = e.getArgument(0, Account.class);
        var correlationid = e.getArgument(1, CorrelationId.class);
        correlations.get(correlationid).complete(a);
    }

    private String generateId(){
        while(true) {
            String uuid = UUID.randomUUID().toString();
            if (!userAccounts.containsKey(uuid)) return uuid;
        }
    }
}