package Payment;

import messaging.Event;
import messaging.MessageQueue;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

public class PaymentService {
//	@Author: Jonathan (s194134)
	private static final String PAYMENT_REQUESTED = "PaymentRequested";
	private static final String PAYMENT_COMPLETED = "PaymentCompleted";
	private static final String TOKEN_VALIDATION_REQUESTED = "TokenValidationRequested";
	private static final String TOKEN_VALIDATION_COMPLETED = "TokenValidationCompleted";
	public static final String BANK_ACCOUNT_REQUESTED = "BankAccountRequested";
	public static final String BANK_ACCOUNT_RECEIVED = "BankAccountReceived";
	private Map<CorrelationId, CompletableFuture<String>> correlations = new ConcurrentHashMap<>();
	private ArrayList<NewPayment> paymentList = new ArrayList<>();
	MessageQueue queue;

	public PaymentService(MessageQueue q) {
		this.queue = q;
		this.queue.addHandler(PAYMENT_REQUESTED, this::handlePaymentRequested);
		this.queue.addHandler(TOKEN_VALIDATION_COMPLETED, this::handleServiceCompleted);
		this.queue.addHandler(BANK_ACCOUNT_RECEIVED, this::handleServiceCompleted);
	}

	private String getBankAccountId(String accountId) {
		// Talks to account management service to get an bank account id
		// Fails if an empty string is returned
		var correlationId = CorrelationId.randomId();
		correlations.put(correlationId,new CompletableFuture<>());
		Event event = new Event(BANK_ACCOUNT_REQUESTED, new Object[] { accountId, correlationId });
		queue.publish(event);
		String bankAccountId = correlations.get(correlationId).join();
		if (bankAccountId.isEmpty()) return "";
		return bankAccountId;
	}
	private String getCustomerAccountIdFromToken(Token token) {
		// Talk to token service at exchange the token for a customer id
		// Fails if an empty string is returned
		var correlationId = CorrelationId.randomId();
		correlations.put(correlationId,new CompletableFuture<>());
		Event event = new Event(TOKEN_VALIDATION_REQUESTED, new Object[] { token, correlationId });
		queue.publish(event);
		String customerId = correlations.get(correlationId).join();
		if (customerId.isEmpty()) return "";
		return customerId;
	}

	public void handleServiceCompleted(Event e) {
		var s = e.getArgument(0, String.class);
		var correlationid = e.getArgument(1, CorrelationId.class);
		correlations.get(correlationid).complete(s);
	}

	private NewPayment makeBankPayment(NewPayment payment, String customerBankId, String merchantBankId) {
//		TODO: Establish payment at bank via library and complete transaction. Save status also
		payment.setPaymentSuccesful(true);
		payment.setErrorMessage("");
		return payment;
	}

	public void handlePaymentRequested(Event ev) {
		NewPayment completedPayment;
		var payment = ev.getArgument(0, NewPayment.class);
		var correlationId = ev.getArgument(1, CorrelationId.class);
		System.out.println("DEBUG: We are here 1");
//		TODO: 1. get customer id from token service
		String customerAccountId = getCustomerAccountIdFromToken(new Token(payment.getCustomerToken()));
		if (customerAccountId.isEmpty()) {
			handleErrors(payment, correlationId, "Customer does not have a valid token");
			return;
		}
		System.out.println("DEBUG: We are here 2");
//		TODO: 2. get merchant & customer bank account from account service
		String customerBankId = getBankAccountId(customerAccountId);
		if (customerBankId.isEmpty()) {
			handleErrors(payment, correlationId, "Customer does not have a valid bank account");
			return;
		}
		System.out.println("DEBUG: We are here 3");
		String merchantBankId = getBankAccountId(payment.getMerchantId());
		if (merchantBankId.isEmpty()) {
			handleErrors(payment, correlationId, "Merchant does not have a valid bank account");
			return;
		}
		System.out.println("DEBUG: We are here 4");
//		TODO: 3. send payment to bank
		completedPayment = makeBankPayment(payment, customerBankId, merchantBankId);
//		TODO: 4. Add payment to log
		paymentList.add(completedPayment);
		System.out.println("DEBUG: We are here 5");
//		TODO: 5. return payment with success or error message
		Event event = new Event(PAYMENT_COMPLETED, new Object[] { completedPayment, correlationId });
		queue.publish(event);
		System.out.println("DEBUG: We are here 6");
//		TODO: 6. Remember to make endpoint for getting payment logs

	}

	public ArrayList<NewPayment> getPaymentLogs() {
		return paymentList;
	}
	 public void resetPaymentLogs() {
		paymentList.clear();
	 }

	public void handleErrors(NewPayment payment, CorrelationId correlationId, String errorMessage) {
		payment.setPaymentSuccesful(false);
		payment.setErrorMessage(errorMessage);
		paymentList.add(payment);
		Event event = new Event(PAYMENT_COMPLETED, new Object[] { payment, correlationId });
		queue.publish(event);
	}
}
