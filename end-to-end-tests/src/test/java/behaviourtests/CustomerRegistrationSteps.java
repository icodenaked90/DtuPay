/*
@Author: Mila s223313
...
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
    private String cid = "not a valid cid";
    private String cAccount;
    private String deregisterReply = "";
    private String registrationReply = "";
    private CustomerAppService customerService = new CustomerAppService();
    private BankService bank = new BankServiceService().getBankServicePort();

    @Given("an unregistered customer")
    public void anUnregisteredCustomer() {
        bankCustomer.setFirstName("Abel");
        bankCustomer.setLastName("Shawn");
        String cpr = CprGenerator.generate();
        bankCustomer.setCprNumber(cpr);
        System.out.println("BANK CUSTOMER:"+bankCustomer.toString());
        try {
            cAccount = bank.createAccountWithBalance(bankCustomer, BigDecimal.valueOf(2000));
        } catch (BankServiceException_Exception e) {
            fail("Invalid bank account.");
        }
        customer = new Account("Abel Shawn", cpr, cAccount);
    }

    @When("the customer has an invalid CPR when being registered in DTUPay")
    public void theCustomerHasAnInvalidCPRWhenBeingRegisteredInDTUPay() {
        customer.setCPR("123");
        registrationReply = customerService.register(customer);
    }

    @When("the customer has an invalid name when being registered in DTUPay")
    public void theCustomerHasAnInvalidNameWhenBeingRegisteredInDTUPay() {
        customer.setName("");
        registrationReply = customerService.register(customer);
    }


    @Given("a registered customer")
    public void aRegisteredCustomer() {
        // Customer has valid bank account
        bankCustomer.setFirstName("Abel");
        bankCustomer.setLastName("Shawn");
        String cpr = CprGenerator.generate();
        bankCustomer.setCprNumber(cpr);
        System.out.println("BANK CUSTOMER:"+bankCustomer.toString());
        try {
            cAccount = bank.createAccountWithBalance(bankCustomer, BigDecimal.valueOf(2000));
        } catch (BankServiceException_Exception e) {
            fail("Invalid bank account.");
        }
        // Create registered customer on DTU Pay
        customer = new Account("Abel Shawn", cpr, cAccount);
        cid = customerService.register(customer);
        assertNotEquals("fail", cid);
    }

    @When("the customer is being registered in DTUPay")
    public void theCustomerIsBeingRegisteredInDTUPay() {
        cid = customerService.register(customer);
        System.out.println("CID: " + cid);
    }

    @Then("the customer receives a DTUPay id")
    public void theCustomerReceivesADTUPayId() {
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

    @Then("the customer receives an error message in registration")
    public void theCustomerReceivesAnErrorMessageInRegistration() {
        assertNotEquals("OK", registrationReply);
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