package behaviourtests;

import clientApp.MerchantAppService;
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

public class MerchantReportSteps {

    private Account merchant;
    private User bankCustomer = new User();
    private String report = "";
    private String mAccount;
    private String response = "";
    private MerchantAppService merchantApp= new MerchantAppService();
    BankService bank = new BankServiceService().getBankServicePort();
    private String mid = "";

    @Given("a successfully registered report merchant")
    public void aSuccessfullyRegisteredReportCustomer() {
        bankCustomer.setFirstName("Decent");
        bankCustomer.setLastName("Merchant");
        bankCustomer.setCprNumber("010170-1976");
        try {
            mAccount = bank.createAccountWithBalance(bankCustomer, BigDecimal.valueOf(2000));
        } catch (BankServiceException_Exception e) {
            fail("Invalid customer bank account.");
        }
        merchant = new Account("Good Customer", "010170-1986", mAccount);
        mid = merchantApp.register(merchant);
    }

    @When("merchant request their report")
    public void merchantRequestTheirReport() {
        response = merchantApp.getReports(mid);
        report = response;
    }

    @Then("the merchant report is received")
    public void theMerchantReportIsReceived() {
        assertNotEquals("fail", report);
    }

    @Given("an unregistered report merchant")
    public void anUnregisteredReportMerchant() {
        mid = "randomId";
    }

    @Then("the merchant receives an empty report")
    public void theMerchantReceivesAnEmptyReport() {
        assertEquals("", response);
    }

    @After()
    public void Cleanup()
    {
        if (mAccount != null) {
            try {
                bank.retireAccount(mAccount);
                mAccount = null;
            } catch (BankServiceException_Exception e) {
                fail("Failed cleanup.");
            }
        }
    }

}
