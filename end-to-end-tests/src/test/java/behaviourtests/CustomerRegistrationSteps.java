/*
@Author: Mila s223313
...
 */

package behaviourtests;
import dtu.ws.fastmoney.*;
import clientApp.CustomerAppService;
import clientApp.models.Account;

import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.Assert.*;
import java.math.BigDecimal;
import jakarta.ws.rs.core.MediaType;

public class CustomerRegistrationSteps {
    private Account customer;
    User bankCustomer = new User();
    private String cid = "not a valid cid";
    private String cAccount;
    private String deregisterReply = "";
    private CustomerAppService customerService = new CustomerAppService();
    BankService bank = new BankServiceService().getBankServicePort();

    @Given("an unregistered customer")
    public void anUnregisteredCustomer() {
        bankCustomer.setFirstName("Johnny");
        bankCustomer.setLastName("Doeluxe");
        bankCustomer.setCprNumber("010170-1999");
        System.out.println("BANK CUSTOMER:"+bankCustomer.toString());
        try {
            cAccount = bank.createAccountWithBalance(bankCustomer, BigDecimal.valueOf(2000));
        } catch (BankServiceException_Exception e) {
            fail("Invalid bank account.");
        }
        customer = new Account("Johnny Doeluxe", "010170-1999", cAccount);
    }

    @Given("a registered customer")
    public void aRegisteredCustomer() {
        // Customer has valid bank account
        bankCustomer.setFirstName("Johnny");
        bankCustomer.setLastName("Doeluxe");
        bankCustomer.setCprNumber("010170-1999");
        System.out.println("BANK CUSTOMER:"+bankCustomer.toString());
        try {
            cAccount = bank.createAccountWithBalance(bankCustomer, BigDecimal.valueOf(2000));
        } catch (BankServiceException_Exception e) {
            fail("Invalid bank account.");
        }
        // Create registered customer on DTU Pay
        customer = new Account("Johnny Doeluxe", "010170-1999", cAccount);
        cid = customerService.register(customer);
        assertNotEquals("fail", cid);
    }

    @When("the customer is being registered in DTUPay")
    public void theCustomerIsBeingRegisteredInDTUPay() {
        cid = customerService.register(customer);
        System.out.println("CID: " + cid);
    }

    @Then("the customer receives an DTUPay id")
    public void theCustomerReceivesAnDTUPayId() {
        assertNotEquals("fail", cid);
    }

    @When("the customer is being deregistered in DTUPay")
    public void theCustomerIsBeingDeregisteredInDTUPay() {
        deregisterReply = customerService.deregister(cid);
    }

    @Then("the customer receives an error message")
    public void theCustomerReceivesAnErrorMessage() {
        assertNotEquals("OK", deregisterReply);
    }

    @Then("the customer is deregistered")
    public void theCustomerIsDeregistered() {
        assertEquals("OK", deregisterReply);
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