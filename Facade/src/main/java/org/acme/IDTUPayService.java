package org.acme;

import messaging.Event;
import org.acme.models.Account;
import org.acme.models.CorrelationId;
import org.acme.models.NewPayment;
import org.acme.models.TokenRequestCommand;

import java.util.concurrent.CompletableFuture;

public interface IDTUPayService {
    String ACCOUNT_REGISTRATION_REQUESTED = "AccountRegistrationRequested";
    String ACCOUNT_ID_ASSIGNED = "AccountIdAssigned";
    String ACCOUNT_DEREGISTRATION_REQUESTED = "AccountDeregistrationRequested";
    String ACCOUNT_DEREGISTRATION_COMPLETED = "AccountDeregistrationCompleted";
    String TOKEN_GENERATION_REQUESTED = "TokenGenerationRequested";
    String TOKEN_GENERATION_COMPLETED = "TokenGenerationCompleted";
    String PAYMENT_REQUESTED = "PaymentRequested";
    String PAYMENT_COMPLETED = "PaymentCompleted";


    String register(Account a);
    void handleAccountIDAssigned(Event e);
    String deregister(String id);
    void handleAccountDeregistrationCompleted(Event e);
    TokenRequestResponse generateTokens(TokenRequestCommand request);
    void handleTokensGenerated(Event e);
    NewPayment pay(NewPayment payment);
    void handlePaymentCompleted(Event e);
}
