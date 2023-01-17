/*
@Author: Mila s223313
@Author: Emily s223122
 */

package behaviourtests;
import dtu.ws.fastmoney.*;
import clientApp.CustomerAppService;
import clientApp.models.Account;
import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import static org.junit.Assert.*;
import java.math.BigDecimal;

public class CustomerRegistrationSteps {
    private Account customer;
    private User bankCustomer = new User();
    private String cid = "";
    private String cAccount;
    private String response = "";
    private CustomerAppService customerApp= new CustomerAppService();
    private BankService bank = new BankServiceService().getBankServicePort();

    @Given("an unregistered customer")
    public void anUnregisteredCustomer() {
        // Customer has valid bank account
        bankCustomer.setFirstName("Test");
        bankCustomer.setLastName("Customer");
        bankCustomer.setCprNumber("010170-1996");
        try {
            cAccount = bank.createAccountWithBalance(bankCustomer, BigDecimal.valueOf(2000));
        } catch (BankServiceException_Exception e) {
            fail("Invalid customer bank account.");
        }
        customer = new Account("Test Customer", "010170-1996", cAccount);
    }

    @Given("a registered customer")
    public void aRegisteredCustomer() {
        // Customer has valid bank account
        bankCustomer.setFirstName("Test");
        bankCustomer.setLastName("Customer");
        bankCustomer.setCprNumber("010170-1996");
        try {
            cAccount = bank.createAccountWithBalance(bankCustomer, BigDecimal.valueOf(2000));
        } catch (BankServiceException_Exception e) {
            fail("Invalid customer bank account.");
        }
        // Create registered customer on DTU Pay
        customer = new Account("Test Customer", "010170-1996", cAccount);
        response = customerApp.register(customer);
        cid = response;
        assertNotEquals("fail", response);
    }

    @When("the customer is being registered in DTUPay")
    public void theCustomerIsBeingRegisteredInDTUPay() {
        // Create registered customer on DTU Pay
        response = customerApp.register(customer);
        cid = response;
    }

    @Then("the customer receives an DTUPay id")
    public void theCustomerReceivesAnDTUPayId() {
        assertNotEquals("fail", cid);
    }

    @When("the customer is being deregistered in DTUPay")
    public void theCustomerIsBeingDeregisteredInDTUPay() {
        response = customerApp.deregister(cid);
    }

    @Then("the customer receives an error message")
    public void theCustomerReceivesAnErrorMessage() {
        assertEquals("fail", response);
    }

    @Then("the customer is deregistered")
    public void theCustomerIsDeregistered() {
        assertEquals("ok", response);
    }

    @Given("an unregistered customer with firstname {string}, lastname {string}, cpr {string}")
    public void anUnregisteredCustomerWithFirstnameLastnameCpr(String arg0, String arg1, String arg2) {
        bankCustomer.setFirstName(arg0);
        bankCustomer.setLastName(arg1);
        bankCustomer.setCprNumber(arg2);
        try {
            cAccount = bank.createAccountWithBalance(bankCustomer, BigDecimal.valueOf(2000));
        } catch (BankServiceException_Exception e) {
            fail("Invalid customer bank account.");
        }
        if (arg0 == "null" || arg1 == "null") {
            customer = new Account(null, arg2, cAccount);
            return;
        }
        if (arg2 == "null") {
            customer = new Account(null, arg2, cAccount);
            return;
        }
        customer = new Account(arg0 + " " + arg1, arg2, cAccount);
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
