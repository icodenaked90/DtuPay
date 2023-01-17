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

import static org.junit.Assert.*;

public class CustomerReportSteps {
    private Account customer;
    private User bankCustomer = new User();
    private String report = "";
    private String cAccount;
    private String response = "";
    private CustomerAppService customerApp= new CustomerAppService();
    BankService bank = new BankServiceService().getBankServicePort();
    private String cid = "";

    @Given("a successfully registered report customer")
    public void aSuccessfullyRegisteredReportCustomer() {
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

    @When("customer request their report")
    public void customerRequestTheirReport() {
        response = customerApp.getReport(cid);
        report = response;
    }

    @Then("the report is received")
    public void theReportIsReceived() {
        assertNotEquals("fail", report);
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

    @Given("an unregistered report customer")
    public void anUnregisteredReportCustomer() {
        cid = "randomId";
    }

    @Then("the customer receives a report error message")
    public void theCustomerReceivesAReportErrorMessage() {
        assertEquals("fail", response);
    }
}
