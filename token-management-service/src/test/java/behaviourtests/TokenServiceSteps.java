/* @Author: Mila (s223313)
   @Author: ...
   @Author: ...
   @Author: ...
 */

package behaviourtests;

import TokenManagement.Token;
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

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class TokenServiceSteps {
    MessageQueue queue = mock(MessageQueue.class);
    TokenService ts = new TokenService(queue);
    String accountId;
    CorrelationId correlationId;
    int numberOfTokens;

    @Given("a customer with existing account id")
    public void aCustomerWithExistingAccountId() {
        // TODO verify customer exists (accountmanagementservice?!?)
        accountId = "Bob";
    }

    @When("a {string} event for generating {int} token is received")
    public void aEventForGeneratingTokenIsReceived(String eventName, int numberOfTokens) {
        this.numberOfTokens = numberOfTokens;
        correlationId = CorrelationId.randomId();
        ts.handleTokenGenerationRequested(new Event(eventName, new Object[] {accountId, numberOfTokens, correlationId}));
    }

    ArrayList<Token> expected;

    @Then("a {string} event is sent")
    public void aEventIsSent(String eventName) {
        expected = new ArrayList<Token>();
        expected.add(Token.generateToken());
        var event = new Event(eventName, new Object[] {expected, correlationId});
        verify(queue).publish(event);
    }

    @And("the customer receives a token")
    public void theCustomerReceivesAToken() {
        assertEquals(numberOfTokens, expected.size());
    }
}
