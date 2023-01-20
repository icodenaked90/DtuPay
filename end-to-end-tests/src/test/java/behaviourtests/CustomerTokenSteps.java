/*
@Author: Mila (s223313)
 */

package behaviourtests;
import clientApp.CustomerAppService;
import clientApp.models.Account;

import clientApp.models.Token;
import clientApp.models.TokenList;
import dtu.ws.fastmoney.BankService;
import dtu.ws.fastmoney.BankServiceException_Exception;
import dtu.ws.fastmoney.BankServiceService;
import dtu.ws.fastmoney.User;
import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.math.BigDecimal;
import java.util.List;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class CustomerTokenSteps {
    private Account customer;
    private User bankCustomer = new User();
    private String cid = "not a valid cid";
    private String cAccount;
    private CustomerAppService customerService = new CustomerAppService();
    private BankService bank = new BankServiceService().getBankServicePort();
    private TokenList ownedTokens;
    private TokenList receivedTokens;
    private String errorMessage = "";

    @Given("the registered customer with {int} tokens")
    public void theRegisteredCustomerWithTokens(int arg0) {
        // Create valid bank account
        bankCustomer.setFirstName("Abel");
        bankCustomer.setLastName("Shawn");
        String cpr = CprGenerator.generate();
        bankCustomer.setCprNumber(cpr);
        try {
            cAccount = bank.createAccountWithBalance(bankCustomer, BigDecimal.valueOf(2000));
        } catch (BankServiceException_Exception e) {
            fail("Invalid bank account.");
        }

        // Create registered customer on DTU Pay
        customer = new Account("Abel Shawn", cpr, cAccount);
        cid = customerService.register(customer);
        assertNotEquals("fail", cid);

        // Get initial tokens
        ownedTokens = new TokenList(new ArrayList<Token>());
        if (arg0 > 0) {
            try
            {
                ownedTokens = customerService.getTokens(cid, arg0);
            }
            catch (Exception e)
            {
                fail(e.getMessage());
            }
        }
    }

    @When("the customer requests {int} tokens")
    public void theCustomerRequestsTokens(int arg0) {
        try {
            receivedTokens = customerService.getTokens(cid, arg0);
            ownedTokens.getTokens().addAll(receivedTokens.getTokens());
        }
        catch (Exception e)
        {
            errorMessage = e.getMessage();
        }
    }

    @Then("the customer receives {int} tokens")
    public void theCustomerReceivesTokens(int arg0) {
        assertEquals(arg0, receivedTokens.getTokens().size());
    }

    @Then("the customer receives the error message {string}")
    public void theCustomerReceivesTheErrorMessage(String arg0) {
        assertEquals(arg0, errorMessage);
    }

    @Then("the customer owns {int} tokens")
    public void theCustomerOwnsTokens(int arg0) {
        assertEquals(arg0, ownedTokens.getTokens().size());
    }

    @After()
    public void Cleanup()
    {
        if (cAccount != null) {
            try {
                bank.retireAccount(cAccount);
                cAccount = null;
            } catch (BankServiceException_Exception e) {
                fail("Failed cleanup.");
            }
        }
    }


}