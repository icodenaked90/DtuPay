package behaviourtests;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import messaging.Event;
import messaging.MessageQueue;
import studentregistration.service.CorrelationId;
import studentregistration.service.Student;
import studentregistration.service.StudentRegistrationService;

public class StudentRegistrationSteps {

	private Map<String,CompletableFuture<Event>> publishedEvents = new HashMap<>();
	
	private MessageQueue q = new MessageQueue() {

		@Override
		public void publish(Event event) {
			var student = event.getArgument(0, Student.class);
			publishedEvents.get(student.getName()).complete(event);
		}

		@Override
		public void addHandler(String eventType, Consumer<Event> handler) {
		}
		
	};
	private StudentRegistrationService service = new StudentRegistrationService(q);
	private CompletableFuture<Student> registeredStudent = new CompletableFuture<>();
	private CompletableFuture<Student> registeredStudent2 = new CompletableFuture<>();
	private Student student;
	private Student student2;
	private Map<Student,CorrelationId> correlationIds = new HashMap<>();
	
	public StudentRegistrationSteps() {
	}

	@Given("there is a student with empty id")
	public void thereIsAStudentWithEmptyId() {
		student = new Student();
		student.setName("James");
		publishedEvents.put(student.getName(), new CompletableFuture<Event>());
		assertNull(student.getId());
	}

	@When("the student is being registered")
	public void theStudentIsBeingRegistered() {
		// We have to run the registration in a thread, because
		// the register method will only finish after the next @When
		// step is executed.
		new Thread(() -> {
			var result = service.register(student);
			registeredStudent.complete(result);
		}).start();
	}

	@Then("the {string} event is published")
	public void theEventIsPublished(String string) {
		Event event = publishedEvents.get(student.getName()).join();
		assertEquals(string,event.getType());
		var st = event.getArgument(0, Student.class);
		var correlationId = event.getArgument(1, CorrelationId.class);
		correlationIds.put(st, correlationId);
	}
	
	@Then("the {string} event is published for the first student")
	public void theEventIsPublishedForTheFirstStudent(String string) {
		Event event = publishedEvents.get(student.getName()).join();
		assertEquals(string,event.getType());
		var st = event.getArgument(0, Student.class);
		var correlationId = event.getArgument(1, CorrelationId.class);
		correlationIds.put(st, correlationId);
	}

	@Then("the {string} event is published for the second student")
	public void theEventIsPublishedForTheSecondStudent(String string) {
		Event event = publishedEvents.get(student2.getName()).join();
		assertEquals(string,event.getType());
		var st = event.getArgument(0, Student.class);
		var correlationId = event.getArgument(1, CorrelationId.class);
		correlationIds.put(st, correlationId);
	}

	@When("the StudentIdAssigned event is received with non-empty id")
	public void theStudentIdAssignedIsReceivedWithNonEmptyId() {
		// This step simulate the event created by a downstream service.
		var c = new Student();
		c.setName(student.getName());
		c.setId("123");
		service.handleStudentIdAssigned(new Event(StudentRegistrationService.STUDENT_ID_ASSIGNED,new Object[] {c,correlationIds.get(student)}));
	}

	@Then("the student is registered and his id is set")
	public void theStudentIsRegisteredAndHisIdIsSet() {
		// Our logic is very simple at the moment; we don't
		// remember that the student is registered.
		assertNotNull(registeredStudent.join().getId());
	}
	
	@Given("another student with empty id")
	public void anotherStudentWithEmptyId() {
		student2 = new Student();
		student2.setName("Second Student");
		publishedEvents.put(student2.getName(), new CompletableFuture<Event>());
	}

	@When("the second student is being registered")
	public void theSecondStudentIsBeingRegistered() {
		new Thread(() -> {
			var result = service.register(student2);
			registeredStudent2.complete(result);
		}).start();
	}

	@When("the StudentIdAssigned event is received for the second student")
	public void theStudentIdAssignedEventIsReceivedForTheSecondStudent() {
		var c = new Student();
		c.setName(student2.getName());
		c.setId("2");
		service.handleStudentIdAssigned(
				new Event(StudentRegistrationService.STUDENT_ID_ASSIGNED, new Object[] { c, correlationIds.get(student2) }));
	}

	@Then("the second student is registered and his id is set")
	public void theSecondStudentIsRegisteredAndHisIdIsSet() {
		var st2 = registeredStudent2.join();
		assertEquals(student2.getName(),st2.getName());
		assertNotNull(st2.getId());
	}

	@When("the StudentIdAssigned event is received for the first student")
	public void theStudentIdAssignedEventIsReceivedForTheFirstStudent() {
		var c = new Student();
		c.setName(student.getName());
		c.setId("1");
		service.handleStudentIdAssigned(new Event(StudentRegistrationService.STUDENT_ID_ASSIGNED,new Object[] {c, correlationIds.get(student)}));
	}

	@Then("the first student is registered and his id is set")
	public void theFirstStudentIsRegisteredAndHisIdIsSet() {
		var st = registeredStudent.join();
		assertEquals(student.getName(),st.getName());
		assertNotNull(st.getId());
	}

}
