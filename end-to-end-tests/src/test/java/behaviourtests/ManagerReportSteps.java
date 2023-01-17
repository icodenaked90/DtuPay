/*
@Author: Emily s223122
 */
package behaviourtests;
import clientApp.CustomerAppService;
import clientApp.ManagerAppService;
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
    private String report = "";
    private String response = "";
    private ManagerAppService managerApp= new ManagerAppService();;
    private String maid = "";

    @Given("a registered manager")
    public void aRegisteredManager() {
        maid = "manager";
    }

    @When("manager request their report")
    public void managerRequestTheirReport() {
        response = managerApp.getReport(maid);
        report = response;
    }

    @Then("the manager report is received")
    public void theManagerReportIsReceived() {
        assertNotEquals("fail", report);
    }
}
