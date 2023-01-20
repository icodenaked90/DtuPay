//	@Author: Jonathan (s194134)
package TestStubs;

import Payment.models.CorrelationId;
import messaging.Event;
import messaging.MessageQueue;

import java.util.HashMap;


public class StubAccountService {
    // @Author: Jonathan (s194134)
    // This test stub simulates a very simple version of Account service.
    // It only contains the things we need to test Payment service

    public static final String BANK_ACCOUNT_REQUESTED = "BankAccountRequested";
    public static final String BANK_ACCOUNT_RECEIVED = "BankAccountReceived";

    private MessageQueue queue;
    private HashMap<String, String> userAccounts = new HashMap<>();

    // @Author: Jonathan (s194134)
    public StubAccountService(MessageQueue q) {
        queue = q;
        queue.addHandler(BANK_ACCOUNT_REQUESTED, this::handleBankAccountRequested);
    }
    public void addAccount(String accountId, String bankAccountId) {
        userAccounts.put(accountId, bankAccountId);
    }

    // @Author: Jonathan (s194134)
    private void handleBankAccountRequested(Event e) {
        var id = e.getArgument(0, String.class);
        var correlationid = e.getArgument(1, CorrelationId.class);
        Event event = new Event(BANK_ACCOUNT_RECEIVED, new Object[]{userAccounts.get(id), correlationid});
        queue.publish(event);
    }

}