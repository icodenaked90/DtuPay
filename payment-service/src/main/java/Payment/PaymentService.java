package Payment;

import java.util.UUID;

import messaging.Event;
import messaging.MessageQueue;

public class PaymentService {

	private static final String STUDENT_ID_ASSIGNED = "StudentIdAssigned";
	private static final String STUDENT_REGISTRATION_REQUESTED = "StudentRegistrationRequested";
	static int id = 0;
	MessageQueue queue;

	public PaymentService(MessageQueue q) {
		this.queue = q;
		this.queue.addHandler(STUDENT_REGISTRATION_REQUESTED, this::handleStudentRegistrationRequested);
	}
	
	private String nextId() {
		id++;
		return Integer.toString(id);
	}

	public void handleStudentRegistrationRequested(Event ev) {
		var s = ev.getArgument(0, Student.class);
		var correlationId = ev.getArgument(1, CorrelationId.class);
		s.setId(nextId());
		Event event = new Event(STUDENT_ID_ASSIGNED, new Object[] { s, correlationId });
		queue.publish(event);
	}
}
