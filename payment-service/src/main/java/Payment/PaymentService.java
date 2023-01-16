package Payment;

import messaging.Event;
import messaging.MessageQueue;

import java.util.ArrayList;

public class PaymentService {
//	@Author: Jonathan (s194134)
	private static final String PAYMENT_COMPLETED = "PaymentCompleted";
	private static final String PAYMENT_REQUESTED = "PaymentRequested";
	private ArrayList<NewPayment> paymentList = new ArrayList<>();
	MessageQueue queue;

	public PaymentService(MessageQueue q) {
		this.queue = q;
		this.queue.addHandler(PAYMENT_REQUESTED, this::handlePaymentRequested);
	}

	private String getMerchantBankId(NewPayment payment) {
		//		TODO: 1. get merchant bank account from account service
		return "asdasd";
	}
	private String getCustomerBankId(NewPayment payment) {
		//		TODO: 2. get customer bank account from token service
		return "asdasd";
	}

	private NewPayment makeBankPayment(NewPayment payment, String customerBankId, String merchantBankId) {
		//		TODO: 3. send payment to bank

		//		TODO: 4. return payment with success or error message
		return payment;
	}

	public void handlePaymentRequested(Event ev) {
		var payment = ev.getArgument(0, NewPayment.class);
		var correlationId = ev.getArgument(1, CorrelationId.class);

		String merchantBankId = getMerchantBankId(payment);

		String customerBankId = getCustomerBankId(payment);

		payment = makeBankPayment(payment, merchantBankId, customerBankId);


		Event event = new Event(PAYMENT_COMPLETED, new Object[] { payment, correlationId });
		queue.publish(event);
	}
}
