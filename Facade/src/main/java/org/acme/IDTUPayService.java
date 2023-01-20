package org.acme;

import messaging.Event;
import org.acme.models.Account;
import org.acme.models.CorrelationId;
import org.acme.models.NewPayment;
import org.acme.models.TokenRequestCommand;

import java.util.concurrent.CompletableFuture;
//@Author: Adin s164432
public interface IDTUPayService {
    String ACCOUNT_REGISTRATION_REQUESTED = "AccountRegistrationRequested";
    String ACCOUNT_ID_ASSIGNED = "AccountIdAssigned";
    String ACCOUNT_DEREGISTRATION_REQUESTED = "AccountDeregistrationRequested";
    String ACCOUNT_DEREGISTRATION_COMPLETED = "AccountDeregistrationCompleted";
    String TOKEN_GENERATION_REQUESTED = "TokenGenerationRequested";
    String TOKEN_GENERATION_COMPLETED = "TokenGenerationCompleted";
    String PAYMENT_REQUESTED = "PaymentRequested";
    String PAYMENT_COMPLETED = "PaymentCompleted";
    String CUSTOMER_LOG_REQUESTED = "CustomerLogRequested";
    String MERCHANT_LOG_REQUESTED = "MerchantLogRequested";
    String MANAGER_LOG_REQUESTED = "ManagerLogRequested";

    String CUSTOMER_LOG_GENERATED = "CustomerLogGenerated";
    String MERCHANT_LOG_GENERATED = "MerchantLogGenerated";
    String MANAGER_LOG_GENERATED = "ManagerLogGenerated";


    String register(Account a);
    void handleAccountIDAssigned(Event e);
    String deregister(String id);
    void handleAccountDeregistrationCompleted(Event e);
    TokenRequestResponse generateTokens(TokenRequestCommand request);
    void handleTokensGenerated(Event e);
    NewPayment pay(NewPayment payment);
    void handlePaymentCompleted(Event e);
    ManagerReportRequestResponse getManagerReport(String id);
    void handleManagerLogGenerated(Event e);
    CustomerReportRequestResponse getCustomerReport(String id);
    void handleCustomerLogGenerated(Event e);
    MerchantReportRequestResponse getMerchantReport(String id);
    void handleMerchantLogGenerated(Event e);
}
