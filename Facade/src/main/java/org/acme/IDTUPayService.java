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


    public String register(Account a) {
        var correlationId = CorrelationId.randomId();
        correlations.put(correlationId, new CompletableFuture<>());
        Event event = new Event(ACCOUNT_REGISTRATION_REQUESTED, new Object[]{a, correlationId});
        queue.publish(event);
        return correlations.get(correlationId).join();
    }

    public void handleAccountIDAssigned(Event e) {
        var id = e.getArgument(0, String.class);
        var correlationid = e.getArgument(1, CorrelationId.class);
        correlations.get(correlationid).complete(id);
    }

    public String deregister(String id) {
        var correlationId = CorrelationId.randomId();
        correlations.put(correlationId, new CompletableFuture<>());
        Event event = new Event(ACCOUNT_DEREGISTRATION_REQUESTED, new Object[]{id, correlationId});
        queue.publish(event);
        return correlations.get(correlationId).join();
    }

    public void handleAccountDeregistrationCompleted(Event e) {
        var errorMessage = e.getArgument(0, String.class); // Empty if no error = Success
        var correlationid = e.getArgument(1, CorrelationId.class);
        correlations.get(correlationid).complete(errorMessage);
    }
    public TokenRequestResponse generateTokens(TokenRequestCommand request) {
        var correlationId = CorrelationId.randomId();
        Tcorrelations.put(correlationId, new CompletableFuture<>());
        Event event = new Event(TOKEN_GENERATION_REQUESTED, new Object[]{request, correlationId});
        queue.publish(event);
        return Tcorrelations.get(correlationId).join();
    }

    public void handleTokensGenerated(Event e) {
        var response = e.getArgument(0, TokenRequestResponse.class);
        var correlationid = e.getArgument(1, CorrelationId.class);
        Tcorrelations.get(correlationid).complete(response);
    }

    public NewPayment pay(NewPayment payment) {
        var correlationId = CorrelationId.randomId();
        Pcorrelations.put(correlationId, new CompletableFuture<>());
        Event event = new Event(PAYMENT_REQUESTED, new Object[]{payment, correlationId});
        queue.publish(event);
        return Pcorrelations.get(correlationId).join();
    }

    public void handlePaymentCompleted(Event e) {
        var response = e.getArgument(0, NewPayment.class);
        var correlationid = e.getArgument(1, CorrelationId.class);
        Pcorrelations.get(correlationid).complete(response);
    }
}
