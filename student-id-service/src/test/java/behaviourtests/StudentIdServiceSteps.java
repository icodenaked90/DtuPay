package behaviourtests;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import messaging.Event;
import messaging.MessageQueue;
import studentid.service.CorrelationId;
import studentid.service.Student;
import studentid.service.StudentIdService;

public class StudentIdServiceSteps {
	MessageQueue queue = mock(MessageQueue.class);
	StudentIdService s = new StudentIdService(queue);
	Student student;
	Student expected;
	private CorrelationId correlationId;

	@When("a {string} event for a student is received")
	public void aEventForAStudentIsReceived(String eventName) {
		student = new Student();
		student.setName("James");
		assertNull(student.getId());
		correlationId = CorrelationId.randomId();
		s.handleStudentRegistrationRequested(new Event(eventName,new Object[] {student, correlationId}));
	}

	@Then("the {string} event is sent with the same correlation id")
	public void theEventIsSentWithTheSameCorrelationId(String eventName) {
		expected = new Student();
		expected.setName("James");
		expected.setId("1");
		var event = new Event(eventName, new Object[] {expected,correlationId});
		verify(queue).publish(event);
	}

	@Then("the student gets a student id")
	public void theStudentGetsAStudentId() {
	    assertNotNull(expected.getId());
	}

}

