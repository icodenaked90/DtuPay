package Payment;

import dtu.ws.fastmoney.BankService;
import dtu.ws.fastmoney.BankServiceException_Exception;
import dtu.ws.fastmoney.BankServiceService;
import messaging.Event;
import messaging.MessageQueue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

public class PaymentService {
//	@Author: Jonathan (s194134)

	// Incoming streams for directed this microservice domain
	private static final String PAYMENT_REQUESTED = "PaymentRequested";
	private static final String PAYMENT_COMPLETED = "PaymentCompleted";
	public static final String PAYMENT_LOGS_REQUESTED = "PaymentLogsRequested";
	public static final String PAYMENT_LOGS_COMPLETED = "PaymentLogsRecieved";

	// For communicating with the Token Service
	private static final String TOKEN_VALIDATION_REQUESTED = "TokenValidationRequested";
	private static final String TOKEN_VALIDATION_COMPLETED = "TokenValidationCompleted";

	// For communicating with the Account Service
	public static final String BANK_ACCOUNT_REQUESTED = "BankAccountRequested";
	public static final String BANK_ACCOUNT_RECEIVED = "BankAccountReceived";

	private Map<CorrelationId, CompletableFuture<String>> correlations = new ConcurrentHashMap<>();
	private ArrayList<NewPayment> paymentList = new ArrayList<>();
	private Map<String, ArrayList<NewPayment>> customerPaymentsReport = new HashMap<>();
	private BankService bank;
	MessageQueue queue;

	public PaymentService(MessageQueue q, BankService b) {
		this.queue = q;
		this.bank = b;
		this.queue.addHandler(PAYMENT_REQUESTED, this::handlePaymentRequested);
		this.queue.addHandler(PAYMENT_LOGS_REQUESTED, this::handlePaymentLogsRequested);
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

	private NewPayment makeBankPayment(NewPayment payment, String customerBankId, String merchantBankId) {
//		TODO: Establish payment at bank via library and complete transaction. Save status also

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

	public void handlePaymentRequested(Event ev) {
		NewPayment completedPayment;
		var payment = ev.getArgument(0, NewPayment.class);
		var correlationId = ev.getArgument(1, CorrelationId.class);

		//	1. get customer id from token service
		String customerAccountId = getCustomerAccountIdFromToken(new Token(payment.getCustomerToken()));
		if (customerAccountId.isEmpty()) {
			handleErrors(payment, correlationId, "Customer does not have a valid token");
			return;
		}


		// 2. get merchant & customer bank account from account service
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


		// 3. send payment to bank
		completedPayment = makeBankPayment(payment, customerBankId, merchantBankId);

		// 4. Add payment to log and reports
		paymentList.add(completedPayment);
		if (customerPaymentsReport.containsKey(customerAccountId)) {
			customerPaymentsReport.get(customerAccountId).add(completedPayment);
		} else {
			ArrayList<NewPayment> tempList = new ArrayList<>();
			tempList.add(completedPayment);
			customerPaymentsReport.put(customerAccountId, tempList);
		}

		// 5. Return completed payment and info
		Event event = new Event(PAYMENT_COMPLETED, new Object[] { completedPayment, correlationId });
		queue.publish(event);
	}

	public void handlePaymentLogsRequested(Event ev) {
		String accountId = ev.getArgument(0, String.class);
		var correlationId = ev.getArgument(1, CorrelationId.class);

		Event event = new Event(PAYMENT_LOGS_COMPLETED, new Object[] {
				customerPaymentsReport.get(accountId), correlationId });
		queue.publish(event);
	}


	// Used for testing
	public ArrayList<NewPayment> getPaymentLogs() {
		return paymentList;
	}
	// Used for testing
	 public void resetPaymentLogs() {
		paymentList.clear();
	 }

	// Used to easily tag and return faulty payments
	public void handleErrors(NewPayment payment, CorrelationId correlationId, String errorMessage) {
		payment.setPaymentSuccesful(false);
		payment.setErrorMessage(errorMessage);
		paymentList.add(payment);
		Event event = new Event(PAYMENT_COMPLETED, new Object[] { payment, correlationId });
		queue.publish(event);
	}
}
