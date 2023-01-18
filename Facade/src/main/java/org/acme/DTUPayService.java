/*
@Author: Mila s223313
@Author: Adin s164432
@Author: Jonathan s194134
 */

package org.acme;

import io.cucumber.gherkin.Token;
import messaging.Event;
import messaging.MessageQueue;
import org.acme.models.NewPayment;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

public class DTUPayService {
    public static final String ACCOUNT_REGISTRATION_REQUESTED = "AccountRegistrationRequested";
    public static final String ACCOUNT_ID_ASSIGNED = "AccountIdAssigned";
    public static final String ACCOUNT_DEREGISTRATION_REQUESTED = "AccountDeregistrationRequested";
    public static final String ACCOUNT_DEREGISTRATION_COMPLETED = "AccountDeregistrationCompleted";
    public static final String TOKEN_GENERATION_REQUESTED = "TokenGenerationRequested";
    public static final String TOKEN_GENERATION_COMPLETED = "TokenGenerationCompleted";
    private static final String PAYMENT_REQUESTED = "PaymentRequested";
    private static final String PAYMENT_COMPLETED = "PaymentCompleted";
    private MessageQueue queue;
    private Map<CorrelationId, CompletableFuture<String>> correlations = new ConcurrentHashMap<>();
    private Map<CorrelationId, CompletableFuture<TokenRequestResponse>> Tcorrelations = new ConcurrentHashMap<>();
    private Map<CorrelationId, CompletableFuture<NewPayment>> Pcorrelations = new ConcurrentHashMap<>();

    public DTUPayService(MessageQueue q) {
        queue = q;
        queue.addHandler(ACCOUNT_ID_ASSIGNED, this::handleAccountIDAssigned);
        queue.addHandler(ACCOUNT_DEREGISTRATION_COMPLETED, this::handleAccountDeregistrationCompleted);
        queue.addHandler(TOKEN_GENERATION_COMPLETED, this::handleTokensGenerated);
        queue.addHandler(PAYMENT_COMPLETED, this::handlePaymentCompleted);
    }

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
