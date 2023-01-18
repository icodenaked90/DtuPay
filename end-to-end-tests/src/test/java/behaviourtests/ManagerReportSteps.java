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

public class ManagerReportSteps {

    @Given("a registered manager")
    public void aRegisteredManager() {
    }

    @When("manager request their report")
    public void managerRequestTheirReport() {
    }

    @Then("the manager report is received")
    public void theManagerReportIsReceived() {
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
