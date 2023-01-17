/*
@Author: Mila (s223313)
@Author: Simon s163595
 */

package behaviourtests;
import clientApp.CustomerAppService;
import clientApp.models.Account;

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
    private List<String> ownedTokens;
    private List<String> receivedTokens;

    @Given("the registered customer with {int} tokens")
    public void theRegisteredCustomerWithTokens(int arg0) {
        // Create valid bank account
        bankCustomer.setFirstName("Abel");
        bankCustomer.setLastName("Shawn");
        bankCustomer.setCprNumber("121212-4444");
        try {
            cAccount = bank.createAccountWithBalance(bankCustomer, BigDecimal.valueOf(2000));
        } catch (BankServiceException_Exception e) {
            fail("Invalid bank account.");
        }

        // Create registered customer on DTU Pay
        customer = new Account("Abel Shawn", "121212-4444", cAccount);
        cid = customerService.register(customer);
        assertNotEquals("fail", cid);

        // Get initial tokens
        ownedTokens = new ArrayList<String>();
        if (arg0 > 0) {
            List<String> tokens = customerService.getTokens(cid, arg0);
            assertNotEquals(null, tokens);
            ownedTokens.addAll(tokens);
        }
    }

    @When("the customer requests {int} tokens")
    public void theCustomerRequestsTokens(int arg0) {
        receivedTokens = customerService.getTokens(cid, arg0);
        if (receivedTokens != null)
            ownedTokens.addAll(receivedTokens);
    }

    @Then("the customer receives {int} tokens")
    public void theCustomerReceivesTokens(int arg0) {
        assertEquals(arg0, receivedTokens.size());
    }

    @Then("the customer owns {int} tokens")
    public void theCustomerOwnsTokens(int arg0) {
        assertEquals(arg0, ownedTokens.size());
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
