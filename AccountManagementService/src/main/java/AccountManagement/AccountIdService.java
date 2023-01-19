/*
@Author: Adin s164432
@Author: Jonathan s194134
@Author: Mila s223313
@Author Hildibjørg s164539
 */

package AccountManagement;

import java.util.HashMap;

import AccountManagement.models.Account;
import AccountManagement.models.CorrelationId;
import messaging.Event;
import messaging.MessageQueue;
import java.util.UUID;

public class AccountIdService implements IAccountIdService{

    private MessageQueue queue;
    private HashMap<String, String> userAccounts = new HashMap<>();


    public AccountIdService(MessageQueue q) {
        queue = q;

        queue.addHandler(ACCOUNT_REGISTRATION_REQUESTED, this::handleAccountRegistrationRequested);
        queue.addHandler(ACCOUNT_DEREGISTRATION_REQUESTED, this::handleAccountDeregistrationRequested);
        queue.addHandler(BANK_ACCOUNT_REQUESTED, this::handleBankAccountRequested);
    }

    public void handleBankAccountRequested(Event e) {
        var id = e.getArgument(0, String.class);
        var correlationid = e.getArgument(1, CorrelationId.class);

        Event event = new Event(BANK_ACCOUNT_RECEIVED, new Object[] { userAccounts.get(id), correlationid });
        queue.publish(event);
    }

    public String register(Account a) {
        //error checking
        if(a.getName().length() <= 0){
            return "Name has a wrong format";
        }
        if(a.getCPR() == null || !a.getCPR().matches("^\\d{6}-?\\d{4}$")) {
            return "CRP number has a wrong format";
        }

        String id = generateId();
        userAccounts.put(id,a.getBankAccount());
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

    // being used in tests to check if account is created
    public HashMap<String, String> getUserAccounts(){
        return userAccounts;
    }
}