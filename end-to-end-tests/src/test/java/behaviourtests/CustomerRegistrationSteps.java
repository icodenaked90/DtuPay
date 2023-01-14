package behaviourtests;

import clientApp.CustomerAppService;
import clientApp.models.Account;
import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.Assert.assertNotEquals;

public class CustomerRegistrationSteps {
    private Account customer;
    private String cid = "";
    private CustomerAppService customerService = new CustomerAppService();

    @Given("^an unregistered customer$")
    public void anUnregisteredCustomer() {
        customer = new Account("Bob", "CPR", "BankID-notvalidatm");
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
}
