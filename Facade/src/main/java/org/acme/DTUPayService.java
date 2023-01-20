package org.acme;

import messaging.Event;
import messaging.MessageQueue;
import org.acme.models.*;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

public class DTUPayService implements IDTUPayService{

    private MessageQueue queue;
    private Map<CorrelationId, CompletableFuture<String>> correlations = new ConcurrentHashMap<>();
    private Map<CorrelationId, CompletableFuture<TokenRequestResponse>> Tcorrelations = new ConcurrentHashMap<>();
    private Map<CorrelationId, CompletableFuture<ManagerReportRequestResponse>> Rcorrelations = new ConcurrentHashMap<>();
    private Map<CorrelationId, CompletableFuture<CustomerReportRequestResponse>> Ccorrelations = new ConcurrentHashMap<>();
    private Map<CorrelationId, CompletableFuture<MerchantReportRequestResponse>> Mcorrelations = new ConcurrentHashMap<>();
    private Map<CorrelationId, CompletableFuture<NewPayment>> Pcorrelations = new ConcurrentHashMap<>();
    //@Author: Adin s164432
    public DTUPayService(MessageQueue q) {
        queue = q;
        queue.addHandler(ACCOUNT_ID_ASSIGNED, this::handleAccountIDAssigned);
        queue.addHandler(ACCOUNT_DEREGISTRATION_COMPLETED, this::handleAccountDeregistrationCompleted);
        queue.addHandler(TOKEN_GENERATION_COMPLETED, this::handleTokensGenerated);
        queue.addHandler(PAYMENT_COMPLETED, this::handlePaymentCompleted);
        queue.addHandler(MANAGER_LOG_GENERATED, this::handleManagerLogGenerated);
        queue.addHandler(CUSTOMER_LOG_GENERATED, this::handleCustomerLogGenerated);
        queue.addHandler(MERCHANT_LOG_GENERATED, this::handleMerchantLogGenerated);
    }
    //@Author: Mila s223313
    public String register(Account a) {
        var correlationId = CorrelationId.randomId();
        correlations.put(correlationId, new CompletableFuture<>());
        Event event = new Event(ACCOUNT_REGISTRATION_REQUESTED, new Object[]{a, correlationId});
        queue.publish(event);
        return correlations.get(correlationId).join();
    }
    //@Author: Adin s164432
    public void handleAccountIDAssigned(Event e) {
        var id = e.getArgument(0, String.class);
        var correlationid = e.getArgument(1, CorrelationId.class);
        correlations.get(correlationid).complete(id);
    }
    //@Author: Mila s223313
    public String deregister(String id) {
        var correlationId = CorrelationId.randomId();
        correlations.put(correlationId, new CompletableFuture<>());
        Event event = new Event(ACCOUNT_DEREGISTRATION_REQUESTED, new Object[]{id, correlationId});
        queue.publish(event);
        return correlations.get(correlationId).join();
    }
    //@Author: Adin s164432
    public void handleAccountDeregistrationCompleted(Event e) {
        var errorMessage = e.getArgument(0, String.class); // Empty if no error = Success
        var correlationid = e.getArgument(1, CorrelationId.class);
        correlations.get(correlationid).complete(errorMessage);
    }
    //@Author: Emily 223122
    public ManagerReportRequestResponse getManagerReport(String id) {
        var correlationId = CorrelationId.randomId();
        Rcorrelations.put(correlationId, new CompletableFuture<>());
        Event event = new Event(MANAGER_LOG_REQUESTED, new Object[]{id, correlationId});
        queue.publish(event);
        ManagerReportRequestResponse response = Rcorrelations.get(correlationId).join();
        return response;
    }
    //@Author: Emily 223122
    public void handleManagerLogGenerated(Event e) {
        var response = e.getArgument(0, ManagerReport.class); // Empty if no error = Success
        var correlationid = e.getArgument(1, CorrelationId.class);
        ManagerReportRequestResponse fullResponse = new ManagerReportRequestResponse();
        fullResponse.setReport(response);
        Rcorrelations.get(correlationid).complete(fullResponse);
    }
    //@Author: Emily 223122
    public CustomerReportRequestResponse getCustomerReport(String id) {
        var correlationId = CorrelationId.randomId();
        Ccorrelations.put(correlationId, new CompletableFuture<>());
        Event event = new Event(CUSTOMER_LOG_REQUESTED, new Object[]{id, correlationId});
        queue.publish(event);
        CustomerReportRequestResponse response = Ccorrelations.get(correlationId).join();
        return response;
    }
    //@Author: Emily 223122
    public void handleCustomerLogGenerated(Event e) {
        var response = e.getArgument(0, CustomerReport.class); // Empty if no error = Success
        var correlationid = e.getArgument(1, CorrelationId.class);

        // convert to a full response we can handle later
        CustomerReportRequestResponse fullResponse = new CustomerReportRequestResponse();
        fullResponse.setReport(response);
        // Set the report and complete the promise
        Ccorrelations.get(correlationid).complete(fullResponse);
    }
    //@Author: Emily 223122
    public MerchantReportRequestResponse getMerchantReport(String id) {
        var correlationId = CorrelationId.randomId();
        Mcorrelations.put(correlationId, new CompletableFuture<>());
        Event event = new Event(MERCHANT_LOG_REQUESTED, new Object[]{id, correlationId});
        queue.publish(event);
        return Mcorrelations.get(correlationId).join();
    }
    //@Author: Emily 223122
    public void handleMerchantLogGenerated(Event e) {
        var response = e.getArgument(0, MerchantReport.class); // Empty if no error = Success
        var correlationid = e.getArgument(1, CorrelationId.class);
        MerchantReportRequestResponse fullResponse = new MerchantReportRequestResponse();
        fullResponse.setReport(response);
        Mcorrelations.get(correlationid).complete(fullResponse);
    }
    //@Author: Adin s164432
    public TokenRequestResponse generateTokens(TokenRequestCommand request) {
        var correlationId = CorrelationId.randomId();
        Tcorrelations.put(correlationId, new CompletableFuture<>());
        Event event = new Event(TOKEN_GENERATION_REQUESTED, new Object[]{request, correlationId});
        queue.publish(event);
        return Tcorrelations.get(correlationId).join();
    }
    //@Author: Adin s164432
    public void handleTokensGenerated(Event e) {
        var response = e.getArgument(0, TokenRequestResponse.class);
        var correlationid = e.getArgument(1, CorrelationId.class);
        Tcorrelations.get(correlationid).complete(response);
    }
    //@Author: Adin s164432
    public NewPayment pay(NewPayment payment) {
        var correlationId = CorrelationId.randomId();
        Pcorrelations.put(correlationId, new CompletableFuture<>());
        Event event = new Event(PAYMENT_REQUESTED, new Object[]{payment, correlationId});
        queue.publish(event);
        return Pcorrelations.get(correlationId).join();
    }
    //@Author: Adin s164432
    public void handlePaymentCompleted(Event e) {
        var response = e.getArgument(0, NewPayment.class);
        var correlationid = e.getArgument(1, CorrelationId.class);
        Pcorrelations.get(correlationid).complete(response);
    }
}
