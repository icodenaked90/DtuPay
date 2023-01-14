/*
@Author: Mila s223313
...
 */

package behaviourtests;

import clientApp.CustomerAppService;
import clientApp.models.Account;
import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class CustomerRegistrationSteps {
    private Account customer;
    private String cid = "not a valid cid";
    private String deregisterReply = "";
    private CustomerAppService customerService = new CustomerAppService();

    @Given("^an unregistered customer$")
    public void anUnregisteredCustomer() {
        customer = new Account("Bob", "CPR", "BankID-notvalidatm");
    }

    @Given("a registered customer")
    public void aRegisteredCustomer() {
        // Create registered customer
        customer = new Account("Bob", "CPR", "BankID-notvalidatm");
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
}
