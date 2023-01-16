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
	private Map<CorrelationId, CompletableFuture<String>> correlations = new ConcurrentHashMap<>();
	private ArrayList<NewPayment> paymentList = new ArrayList<>();
	MessageQueue queue;

	public PaymentService(MessageQueue q) {
		this.queue = q;
		this.queue.addHandler(PAYMENT_REQUESTED, this::handlePaymentRequested);
	}

	private String getBankAccountId(String accountId) {
//		TODO: Communicate with account service and exchange info
 		return "asdasd";
	}
	private String getCustomerAccountIdFromToken(Token token) {
//		Talk to token service at exchange the token for a customer id
//		Fails if an empty string is returned
		var correlationId = CorrelationId.randomId();
		correlations.put(correlationId,new CompletableFuture<>());
		Event event = new Event(TOKEN_VALIDATION_REQUESTED, new Object[] { token, correlationId });
		queue.publish(event);
		String customerId = correlations.get(correlationId).join();
		if (customerId.isEmpty()) return "";
		return customerId;
	}

	private NewPayment makeBankPayment(NewPayment payment, String customerBankId, String merchantBankId) {
//		TODO: Establish payment at bank via library and complete transaction. Save status also
		payment.setPaymentSuccesful(true);
		payment.setErrorMessage("");
		return payment;
	}

//	Skal måske være synchronized
	public void handlePaymentRequested(Event ev) {
		NewPayment completedPayment;
		var payment = ev.getArgument(0, NewPayment.class);
		var correlationId = ev.getArgument(1, CorrelationId.class);

//		TODO: 1. get customer id from token service
		String customerAccountId = getCustomerAccountIdFromToken(new Token(payment.getCustomerToken()));
		if (customerAccountId.isEmpty()) {
			handleErrors(payment, correlationId, "Customer does not have a valid token");
			return;
		}
//		TODO: 2. get merchant & customer bank account from account service
		String customerBankId = getBankAccountId(customerAccountId);
		if (customerBankId.isEmpty()) {
			handleErrors(payment, correlationId, "Customer does not have a valid bank account");
			return;
		}

		String merchantBankId = getBankAccountId(payment.getMerchantId());
		if (merchantBankId.isEmpty()) {
			handleErrors(payment, correlationId, "Merchant does not have a valid bank account");
			return;
		}


//		TODO: 3. send payment to bank
		completedPayment = makeBankPayment(payment, customerBankId, merchantBankId);
//		TODO: 4. Add payment to log

//		TODO: 5. return payment with success or error message
		Event event = new Event(PAYMENT_COMPLETED, new Object[] { completedPayment, correlationId });
		queue.publish(event);
	}

	public void handleErrors(NewPayment payment, CorrelationId correlationId, String errorMessage) {
		payment.setPaymentSuccesful(false);
		payment.setErrorMessage(errorMessage);
		Event event = new Event(PAYMENT_COMPLETED, new Object[] { payment, correlationId });
		queue.publish(event);
	}
}
