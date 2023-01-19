//	@Author: Jonathan (s194134)

package Payment;

import Payment.models.*;
import dtu.ws.fastmoney.BankService;
import dtu.ws.fastmoney.BankServiceException_Exception;
import messaging.Event;
import messaging.MessageQueue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

public class PaymentService implements IPaymentService {

    // Maps for keeping track of responses
    private Map<CorrelationId, CompletableFuture<String>> correlationsToken = new ConcurrentHashMap<>();
    private Map<CorrelationId, CompletableFuture<String>> correlationsBank = new ConcurrentHashMap<>();

    // List for storing information used in testing
    private ArrayList<NewPayment> paymentList = new ArrayList<>();

    // Our log kept for report service
    private TransactionLog transactionLog = new TransactionLog();


    private BankService bank;
    MessageQueue queue;

    /**
     * Constructor to create bank and our message queue
     * also attaches handlers to the queue.
     *
     * @param q MessageQueue
     * @param b BankService
     */
    public PaymentService(MessageQueue q, BankService b) {
        this.queue = q;
        this.bank = b;
        this.queue.addHandler(PAYMENT_REQUESTED, this::handlePaymentRequested);
        this.queue.addHandler(PAYMENT_LOGS_REQUESTED, this::handlePaymentLogsRequested);
        this.queue.addHandler(TOKEN_VALIDATION_COMPLETED, this::handleTokenServiceCompleted);
        this.queue.addHandler(BANK_ACCOUNT_RECEIVED, this::handleBankServiceCompleted);

    }

    /**
     * Talks to account management service to get a bank account id
     * Fails if an empty string is returned
     *
     * @param accountId
     * @return bankAccountId (String)
     */
    private String getBankAccountId(String accountId) {
        //
        var correlationId = CorrelationId.randomId();
        correlationsBank.put(correlationId, new CompletableFuture<>());
        Event event = new Event(BANK_ACCOUNT_REQUESTED, new Object[]{accountId, correlationId});
        queue.publish(event);
        String bankAccountId = correlationsBank.get(correlationId).join();
        if (bankAccountId == null) return "";
        return bankAccountId;
    }

    /**
     * Makes payment through fastmoney lib.
     *
     * @param payment
     * @param customerBankId
     * @param merchantBankId
     * @return completed payment object with payment info updated
     */
    private NewPayment makeBankPayment(NewPayment payment, String customerBankId, String merchantBankId) {

        try {
            bank.transferMoneyFromTo(customerBankId, merchantBankId, BigDecimal.valueOf(payment.getAmount()), "DTUPay");
            payment.setPaymentSuccesful(true);
            payment.setErrorMessage("");
        } catch (BankServiceException_Exception e) {
            payment.setPaymentSuccesful(false);
            payment.setErrorMessage(e.getMessage());
        }
        return payment;
    }

    /***
     * Talk to token service at exchange the token for a customer id
     * Fails if an empty string is returned
     * @param token
     * @return customerId
     */
    private String getCustomerAccountIdFromToken(Token token) {

        var correlationId = CorrelationId.randomId();
        correlationsToken.put(correlationId, new CompletableFuture<>());
        Event event = new Event(TOKEN_VALIDATION_REQUESTED, new Object[]{token, correlationId});
        queue.publish(event);
        String customerId = correlationsToken.get(correlationId).join();
        if (customerId.isEmpty()) return "";
        return customerId;
    }

    /**
     * Handles event from token service communication
     *
     * @param e
     */
    public void handleTokenServiceCompleted(Event e) {
        var s = e.getArgument(0, String.class);
        var correlationid = e.getArgument(1, CorrelationId.class);
        correlationsToken.get(correlationid).complete(s);
    }

    /**
     * Handles event from account service communication
     *
     * @param e
     */
    public void handleBankServiceCompleted(Event e) {
        var s = e.getArgument(0, String.class);
        var correlationid = e.getArgument(1, CorrelationId.class);
        correlationsBank.get(correlationid).complete(s);
    }


    /**
     * Handles events from the payment requested queue.
     * Does the following steps:
     * 1. get customer id from token service
     * 2. get merchant & customer bank account from account service
     * 3. send payment to bank
     * 4. Add payment to log and reports
     * 5. Return completed payment and info
     *
     * @param e
     */
    public void handlePaymentRequested(Event e) {
        NewPayment completedPayment;
        var payment = e.getArgument(0, NewPayment.class);
        var correlationId = e.getArgument(1, CorrelationId.class);

        //	1. get customer id from token service
        String customerAccountId = getCustomerAccountIdFromToken(new Token(payment.getCustomerToken()));
        if (customerAccountId.isEmpty()) {
            handleErrors(payment, correlationId, "Customer does not have a valid token");
            return;
        }

        // 2. get merchant & customer bank account from account service
        // Customer fetch
        String customerBankId = getBankAccountId(customerAccountId);
        if (customerBankId.isEmpty()) {
            handleErrors(payment, correlationId, "Customer does not have a valid bank account");
            return;
        }
        // Merchant fetch
        String merchantBankId = getBankAccountId(payment.getMerchantId());
        if (merchantBankId.isEmpty()) {
            handleErrors(payment, correlationId, "Merchant does not have a valid bank account");
            return;
        }


        // 3. send payment to bank
        completedPayment = makeBankPayment(payment, customerBankId, merchantBankId);

        // 4. Add payment to log and reports
        paymentList.add(completedPayment);

        transactionLog.addToLog(new Transaction(
                payment.getCustomerToken(),
                customerAccountId,
                payment.getMerchantId(),
                payment.getAmount()
        ));

        // 5. Return completed payment and info
        Event event = new Event(PAYMENT_COMPLETED, new Object[]{completedPayment, correlationId});
        queue.publish(event);
    }

    /**
     * Handles event from report service communication
     *
     * @param e
     */
    public void handlePaymentLogsRequested(Event e) {
        String accountId = e.getArgument(0, String.class);
        transactionLog.setId(accountId);
        var correlationId = e.getArgument(1, CorrelationId.class);

        Event event = new Event(PAYMENT_LOGS_COMPLETED, new Object[]{transactionLog, correlationId});
        queue.publish(event);
    }


    // Used for testing
    public ArrayList<NewPayment> getPaymentLogs() {
        return paymentList;
    }

    // Used for testing
    public void resetPaymentList() {
        paymentList.clear();
    }

    /**
     * Used to easily tag and return faulty payments
     *
     * @param payment
     * @param correlationId
     * @param errorMessage
     */
    public void handleErrors(NewPayment payment, CorrelationId correlationId, String errorMessage) {
        payment.setPaymentSuccesful(false);
        payment.setErrorMessage(errorMessage);
        paymentList.add(payment);
        Event event = new Event(PAYMENT_COMPLETED, new Object[]{payment, correlationId});
        queue.publish(event);
    }
}
