/*
@Author: Mila (s223313)
@Author: Adin (s164432)
 */

package behaviourtests;

import TokenManagement.*;
import TokenManagement.models.CorrelationId;
import TokenManagement.models.Token;
import TokenManagement.models.TokenList;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import messaging.Event;
import messaging.MessageQueue;

import java.util.ArrayList;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;


public class TokenServiceSteps {
    MessageQueue queue = mock(MessageQueue.class);
    CorrelationId correlationId;
    TokenService ts = new TokenService(queue);
    ArrayList<Token> preOwned = new ArrayList<>();

    private TokenList expectedgen =new TokenList(new ArrayList<>());
    private String expectedval;
    private String cid;
    private int amount;


    @Given("a customer")
    public void aCustomer() {
        cid = "cakebornmenace";
    }


    @When("a {string} event for generating {int} token is received")
    public void aEventForGeneratingTokenIsReceived(String eventName, int numberOfTokens) {
        correlationId = CorrelationId.randomId();
        amount = numberOfTokens;
        ts.handleTokenGenerationRequested(new Event(eventName, new Object[] {new TokenRequestCommand(cid, numberOfTokens), correlationId}));
    }


    @Then("a {string} event is sent")
    public void aEventIsSent(String eventName) {
        if(eventName.equals("TokenGenerationCompleted")) {
            Map<Token, String> map = ts.getUnused();
            ArrayList<Token> temp = new ArrayList<>();
            assertNotNull(map);


            for (Token token : map.keySet()) {
                if (map.get(token).equals(cid)) {
                    if (!preOwned.contains(token)) {
                        temp.add(token);
                    }
                }
                expectedgen.setTokens(temp);
            }
        }
        else {

            var event = new Event(eventName, new Object[]{expectedval, correlationId});

            verify(queue).publish(event);
        }
    }

    @And("the customer receives {int} token")
    public void theCustomerReceivesToken(int numberOfTokens) {
        assertEquals(numberOfTokens, expectedgen.getTokens().size());
    }



    @And("the customer has {int} token")
    public void theCustomerHasToken(int amount) {
        preOwned =ts.addTokensToAccount(cid,amount);
    }

    @When("a {string} event is received with the existing token")
    public void aEventIsReceivedWithTheExistingToken(String eventName) {
        correlationId = CorrelationId.randomId();
        expectedval = cid;
        ts.handleTokenValidationRequested(new Event(eventName, new Object[] { preOwned.get(0), correlationId}));
    }

    @When("a {string} event is received with a fake token")
    public void aEventIsReceivedWithAFakeToken(String eventName) {
        correlationId = CorrelationId.randomId();
        expectedval = "";
        ts.handleTokenValidationRequested(new Event(eventName, new Object[] {Token.generateToken(), correlationId}));
    }
}
