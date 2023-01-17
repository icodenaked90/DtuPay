/* @Author: Mila (s223313)
   @Author: ...
   @Author: ...
   @Author: ...
 */

package behaviourtests;

import TokenManagement.Token;
import TokenManagement.TokenRequestCommand;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import messaging.Event;
import messaging.MessageQueue;

import TokenManagement.TokenService;
import TokenManagement.CorrelationId;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

// @Author: Adin (s164432)
public class TokenServiceSteps {
    MessageQueue queue = mock(MessageQueue.class);

    TokenService ts = new TokenService(queue);
    String accountId;
    private String cid;
    CorrelationId correlationId;


    @Given("a customer")
    public void aCustomer() {
        cid = "cakebornmenace";
    }
    private int amount;
    @When("a {string} event for generating {int} token is received")
    public void aEventForGeneratingTokenIsReceived(String eventName, int numberOfTokens) {
        correlationId = CorrelationId.randomId();
        amount = numberOfTokens;
        ts.handleTokenGenerationRequested(new Event(eventName, new Object[] {new TokenRequestCommand(cid, numberOfTokens), correlationId}));
    }

    private ArrayList<Token> expected;

    @Then("a {string} event is sent")
    public void aEventIsSent(String eventName) {
        //TODO:
    }

    @And("the customer receives {int} token")
    public void theCustomerReceivesToken(int numberOfTokens) {
        assertEquals(numberOfTokens, expected.size());
    }



    @And("the customer has {int} token")
    public void theCustomerHasToken(int arg0) {
    }

    @When("a {string} event is received with the existing token")
    public void aEventIsReceivedWithTheExistingToken(String arg0) {
    }

    @And("the correct id is received")
    public void theCorrectIdIsReceived() {
    }

    @When("a {string} event is received with a fake token")
    public void aEventIsReceivedWithAFakeToken(String arg0) {
    }

    @And("an empty id is received")
    public void anEmptyIdIsReceived() {
    }
}
