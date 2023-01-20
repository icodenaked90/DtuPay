/*
@Author: Emily s223122
 */
package behaviourtests;
import clientApp.CustomerAppService;
import clientApp.ManagerAppService;
import clientApp.models.Account;
import clientApp.models.ManagerReport;
import clientApp.models.ManagerReportEntry;
import dtu.ws.fastmoney.BankService;
import dtu.ws.fastmoney.BankServiceException_Exception;
import dtu.ws.fastmoney.BankServiceService;
import dtu.ws.fastmoney.User;
import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.wildfly.common.Assert;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class ManagerReportSteps {

    String manId;
    ManagerReport report;
    ManagerAppService service = new ManagerAppService();

    @Given("a registered manager")
    public void aRegisteredManager() {
        manId = "manager";
    }

    @When("manager request their report")
    public void managerRequestTheirReport() {
        report = service.getReports(manId);
    }

    @Then("the manager report is received")
    public void theManagerReportIsReceived() {
        ManagerReport report1 = new ManagerReport();
        var log = new ManagerReportEntry();
        log.setAmount(10);
        log.setToken("aaaa");
        log.setCid("cad");
        log.setMid("gfd");
        report1.addToLog(log);
        System.out.println(report.getLog().get(0).getCid());
        System.out.println(report1.getLog().get(0).getCid());
        Assert.assertTrue(report1.equals(report));
    }
}
