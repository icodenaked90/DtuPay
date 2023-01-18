// @Author Hildibjørg (s164539)
package behaviourtests;

import AccountManagement.AccountIdService;
import AccountManagement.CorrelationId;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import messaging.Event;
import messaging.MessageQueue;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

import AccountManagement.Account;

import java.util.HashMap;

public class AccountIdServiceSteps {
    MessageQueue queue = mock(MessageQueue.class);
    AccountIdService service = new AccountIdService(queue);
    CorrelationId correlationId;
    HashMap<String, String> formerAccounts;

    Account account;
    @Given("there is an account with an empty id")
    public void thereIsAnAccountWithAnEmptyId() {
        account = new Account();
        account.setName("Corbin Bleu");
        account.setCPR("1701931111");
        account.setBankAccount("1234567890");
    }
    @When("a {string} for generating account is received")
    public void aForGeneratingAccountIsReceived(String event_type) {
        formerAccounts = service.getUserAccounts();

        correlationId = CorrelationId.randomId();
        service.handleAccountRegistrationRequested(new Event(event_type, new Object[] {account, correlationId}));

    }


    @And("the account is registered")
    public void theAccountIsRegistered() {
        if(!formerAccounts.containsValue(account.getBankAccount())){
            if(service.getUserAccounts().containsValue(account.getBankAccount())){
                assertTrue(true);
            }
        }
        else {
            assertFalse(false);
        }
    }
}
