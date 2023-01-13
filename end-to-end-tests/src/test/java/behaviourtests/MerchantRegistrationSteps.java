package behaviourtests;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.concurrent.CompletableFuture;

import clientApp.MerchantAppService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class MerchantRegistrationSteps {
	MerchantAppService service = new MerchantAppService();
	private Merchant merchant;
	private CompletableFuture<Merchant> result = new CompletableFuture<Merchant>();
	private Merchant merchant1;
	private CompletableFuture<Merchant> result2 = new CompletableFuture<Merchant>();

//	@Given("another unregistered student")
//	public void anotherUnregisteredStudent() {
//		merchant1 = new Merchant();
//		merchant1.setName("Second Student");
//	}
//
//	@When("the two students are registered at the same time")
//	public void theTwoStudentsAreRegisteredAtTheSameTime() {
//		var thread1 = new Thread(() -> {
//			result.complete(service.register(merchant));
//		});
//		var thread2 = new Thread(() -> {
//			result2.complete(service.register(merchant1));
//		});
//		thread1.start();
//		thread2.start();
//	}
//
//	@Then("the first student has a non empty id")
//	public void theFirstStudentHasANonEmptyId() {
//		var st = result.join();
//		assertEquals(merchant.getName(), st.getName());
//		assertNotNull(st.getId());
//	}
//
//	@Then("the second student has a non empty id different from the first student")
//	public void theSecondStudentHasANonEmptyIdDifferentFromTheFirstStudent() {
//		var st = result.join();
//		var st2 = result2.join();
//		assertEquals(merchant1.getName(), st2.getName());
//		assertNotNull(st2.getId());
//		assertNotEquals(st.getId(), st2.getId());
//	}
}
