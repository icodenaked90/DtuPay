package behaviourtests;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.concurrent.CompletableFuture;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class Steps {
	StudentRegistrationService service = new StudentRegistrationService();
	private Student student;
	private CompletableFuture<Student> result = new CompletableFuture<Student>();
	private Student student2;
	private CompletableFuture<Student> result2 = new CompletableFuture<Student>();

	@Given("an unregistered student with empty id")
	public void anUnregisteredStudentWithEmptyId() {
		student = new Student();
		student.setName("James");
		assertNull(student.getId());
	}

	@When("the student is being registered")
	public void theStudentIsBeingRegistered() {
		result.complete(service.register(student));
	}

	@Then("the student is registered")
	public void thenTheStudentIsRegistered() {
		// test needs to go here
	}

	@Then("has a non empty id")
	public void hasANonEmptyId() {
		assertNotNull(result.join().getId());
	}

	@Given("another unregistered student")
	public void anotherUnregisteredStudent() {
		student2 = new Student();
		student2.setName("Second Student");
	}

	@When("the two students are registered at the same time")
	public void theTwoStudentsAreRegisteredAtTheSameTime() {
		var thread1 = new Thread(() -> {
			result.complete(service.register(student));
		});
		var thread2 = new Thread(() -> {
			result2.complete(service.register(student2));
		});
		thread1.start();
		thread2.start();
	}

	@Then("the first student has a non empty id")
	public void theFirstStudentHasANonEmptyId() {
		var st = result.join();
		assertEquals(student.getName(), st.getName());
		assertNotNull(st.getId());
	}

	@Then("the second student has a non empty id different from the first student")
	public void theSecondStudentHasANonEmptyIdDifferentFromTheFirstStudent() {
		var st = result.join();
		var st2 = result2.join();
		assertEquals(student2.getName(), st2.getName());
		assertNotNull(st2.getId());
		assertNotEquals(st.getId(), st2.getId());
	}
}
