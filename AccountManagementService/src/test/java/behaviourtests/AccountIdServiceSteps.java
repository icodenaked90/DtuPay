// @Author: Hildibjørg (s164539)
// @Author: Mila (s223313)

package behaviourtests;

import AccountManagement.AccountIdService;
import AccountManagement.models.CorrelationId;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import messaging.Event;
import messaging.MessageQueue;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

import AccountManagement.models.Account;

import java.util.HashMap;
import java.util.Map.Entry;

public class AccountIdServiceSteps {
    MessageQueue queue = mock(MessageQueue.class);
    AccountIdService service = new AccountIdService(queue);
    CorrelationId correlationId;
    HashMap<String, String> formerAccounts;
    String accountId;

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

    //Deregistration
    @Given("there is an preexisting account with an id")
    public void thereIsAnPreexistingAccountWithAnId() {
        account = new Account();
        account.setName("Corbin Bleu");
        account.setCPR("1701931111");
        account.setBankAccount("1234567890");

        correlationId = CorrelationId.randomId();

        service.handleAccountRegistrationRequested(new Event("AccountRegistrationRequested", new Object[] {account, correlationId}));
        formerAccounts = service.getUserAccounts();

        for(Entry<String, String> entry: formerAccounts.entrySet()){
            if(entry.getValue().equals(account.getBankAccount())) {
                accountId = entry.getKey();
            }
        }
        System.out.println(accountId);
    }

    @When("a {string} for deleting an account is received")
    public void aForDeletingAnAccountIsReceived(String event_type) {
        service.handleAccountDeregistrationRequested(new Event(event_type, new Object[] {accountId, correlationId}));
        formerAccounts = service.getUserAccounts();
    }

    @And("the account is deregistered")
    public void theAccountIsDeregistered() {
        if(!formerAccounts.containsValue(account.getBankAccount())){
            assertTrue(true);
        }
        else {
            assertFalse(false);
        }
    }

    @Given("there is an account with an empty name and no id")
    public void thereIsAnAccountWithAnEmptyNameAndNoId() {
        account = new Account();
        account.setName("");
        account.setCPR("1701931111");
        account.setBankAccount("1234567890");
    }
    @Given("there is an account with an invalid CPR number and no id")
    public void thereIsAnAccountWithAnInvalidCPRNumberAndNoId() {
        account = new Account();
        account.setName("Sharpay Evans");
        account.setCPR("hello");
        account.setBankAccount("1234567890");
    }

    @And("the account is not registered")
    public void TheAccountIsNotRegistered(){
        if(!formerAccounts.containsValue(account.getBankAccount())){
            if(!service.getUserAccounts().containsValue(account.getBankAccount())){
                assertTrue(true);
            }
        }
        else {
            assertFalse(false);
        }
    }
}
