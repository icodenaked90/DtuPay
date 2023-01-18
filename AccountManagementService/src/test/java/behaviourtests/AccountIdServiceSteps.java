package behaviourtests;
// @Author Hildibjørg (s164539)

import AccountManagement.AccountIdService;
import AccountManagement.CorrelationId;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import messaging.Event;
import messaging.MessageQueue;

import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import AccountManagement.Account;

import java.util.concurrent.CompletableFuture;

public class AccountIdServiceSteps {
    MessageQueue queue = mock(MessageQueue.class);
    CompletableFuture<String> regAcc = new CompletableFuture<>();
    AccountIdService service = new AccountIdService(queue);
    CorrelationId correlationId;

    Account account;
    @Given("there is an account with an empty id")
    public void thereIsAnAccountWithAnEmptyId() {
        account = new Account();
        account.setName("Corbin Bleu");
        account.setCPR("1701931111");
        account.setBankAccount("1234567890");
    }

    @When("the account is being registered")
    public void theAccountIsBeingRegistered() {
        new Thread(() -> {
            var result = service.register(account);
            regAcc.complete(result);
        }).start();
    }
    @When("a {string} for generating account is received")
    public void aForGeneratingAccountIsReceived(String event_type) {
        correlationId = CorrelationId.randomId();
        service.handleAccountRegistrationRequested(new Event(event_type, new Object[] {account, correlationId}));

    }
    @Then("the {string} event is sent")
    public void theEventIsSent(String event_type) {
        var event = new Event(event_type, new Object[] {account, correlationId});
        verify(queue).publish(event);
    }


}
