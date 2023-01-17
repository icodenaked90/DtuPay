/*
@Author: Emily s223122
 */
package behaviourtests;
import clientApp.CustomerAppService;
import clientApp.models.Account;
import dtu.ws.fastmoney.BankService;
import dtu.ws.fastmoney.BankServiceException_Exception;
import dtu.ws.fastmoney.BankServiceService;
import dtu.ws.fastmoney.User;
import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class CustomerTokenSteps {
    private CustomerAppService customerApp= new CustomerAppService();
    private User bankCustomer = new User();
    private String cid = "";
    private String response = "";
    private String tokens = "";
    private Account customer;
    private String cAccount;
    BankService bank = new BankServiceService().getBankServicePort();

    @Given("a successfully registered customer")
    public void aSuccessfullyRegisteredCustomer() {
        bankCustomer.setFirstName("Decent");
        bankCustomer.setLastName("Customer");
        bankCustomer.setCprNumber("010170-1976");
        try {
            cAccount = bank.createAccountWithBalance(bankCustomer, BigDecimal.valueOf(2000));
        } catch (BankServiceException_Exception e) {
            fail("Invalid customer bank account.");
        }
        customer = new Account("Good Customer", "010170-1986", cAccount);
        cid = customerApp.register(customer);
    }

    @When("request {int} tokens")
    public void requestTokens(int arg0) {
        response = customerApp.getToken(cid, arg0);
        tokens = response;
    }

    @Then("then {int} tokens is received")
    public void thenTokensIsReceived(int arg0) {
        assertEquals(tokens.split(",").length, arg0);
    }

    @Then("the customer receives a token error message")
    public void theCustomerReceivesATokenErrorMessage() {
        assertEquals("fail", response);
    }

    @Given("a unregistered customer")
    public void aUnregisteredCustomer() {
        cid = "randomId";
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
