package Payment;

import messaging.Event;
//@Author Adin s164432
public interface IPaymentService {
    // Incoming streams for directed this microservice domain
    String PAYMENT_REQUESTED = "PaymentRequested";
    String PAYMENT_COMPLETED = "PaymentCompleted";
    String PAYMENT_LOGS_REQUESTED = "FullLogRequested";
    String PAYMENT_LOGS_COMPLETED = "FullLogGenerated";

    // For communicating with the Token Service
    String TOKEN_VALIDATION_REQUESTED = "TokenValidationRequested";
    String TOKEN_VALIDATION_COMPLETED = "TokenValidationCompleted";

    // For communicating with the Account Service
    String BANK_ACCOUNT_REQUESTED = "BankAccountRequested";
    String BANK_ACCOUNT_RECEIVED = "BankAccountReceived";


    void handlePaymentRequested(Event e);
    void handlePaymentLogsRequested(Event e);
    void handleTokenServiceCompleted(Event e);
    void handleBankServiceCompleted(Event e);

}
