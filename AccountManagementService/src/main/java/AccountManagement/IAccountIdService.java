package AccountManagement;

import messaging.Event;

public interface IAccountIdService {

    String ACCOUNT_REGISTRATION_REQUESTED = "AccountRegistrationRequested";
    String ACCOUNT_ID_ASSIGNED = "AccountIdAssigned";
    String ACCOUNT_DEREGISTRATION_REQUESTED = "AccountDeregistrationRequested";
    String ACCOUNT_DEREGISTRATION_COMPLETED = "AccountDeregistrationCompleted";
    String BANK_ACCOUNT_REQUESTED = "BankAccountRequested";
    String BANK_ACCOUNT_RECEIVED = "BankAccountReceived";

    //@Author Adin s166432
    void handleBankAccountRequested(Event e);
    //@Author Adin s166432
    void handleAccountRegistrationRequested(Event e);
}
