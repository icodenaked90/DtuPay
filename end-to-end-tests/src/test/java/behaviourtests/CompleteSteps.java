//@Author: Emily s223122

package behaviourtests;

import clientApp.CustomerAppService;
import clientApp.ManagerAppService;
import clientApp.MerchantAppService;
import clientApp.models.*;
import dtu.ws.fastmoney.BankService;
import dtu.ws.fastmoney.BankServiceException_Exception;
import dtu.ws.fastmoney.BankServiceService;
import dtu.ws.fastmoney.User;
import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

import java.math.BigDecimal;

import static org.junit.Assert.*;


public class CompleteSteps {
    CustomerReport creport;
    private Account customer;
    private Account merchant;
    private User bankCustomer = new User();
    private MerchantReport report;
    private String cAccount;
    private String mAccount;
    private MerchantAppService merchantApp = new MerchantAppService();
    private CustomerAppService customerApp = new CustomerAppService();
    BankService bank = new BankServiceService().getBankServicePort();
    private String cid = "";
    private String mid = "";
    TokenList token;

    @Given("an registered complete customer")
    public void anRegisteredCompleteCustomer() {
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
        cid = customerApp.register(customer);
        assertNotEquals("fail", cid);
    }

    @And("an registered complete merchant")
    public void anRegisteredCompleteMerchant() {
        bankCustomer.setFirstName("Decent");
        bankCustomer.setLastName("Merchant");
        String cpr = CprGenerator.generate();
        bankCustomer.setCprNumber(cpr);
        try {
            mAccount = bank.createAccountWithBalance(bankCustomer, BigDecimal.valueOf(2000));
        } catch (BankServiceException_Exception e) {
            fail("Invalid customer bank account.");
        }
        merchant = new Account("Good Customer", cpr, mAccount);
        mid = merchantApp.register(merchant);
    }

    @Then("complete customer request a token")
    public void completeCustomerRequestAToken() {
        try {
            token = customerApp.getTokens(cid, 1);
        }
        catch (Exception e)
        {
        }
    }

    @And("complete merchant receives payment")
    public void completeMerchantReceivesPayment() {
        merchantApp.pay(token.getTokens().get(0).getId(), mid, 10);
    }

    @And("complete merchant request log")
    public void completeMerchantRequestLog() {
        report = merchantApp.getReports(mid);
        creport = customerApp.getReport(cid);
    }

    @Then("log contains payment")
    public void logContainsPayment() {
        MerchantReport expectedReport = new MerchantReport();
        MerchantReportEntry expectedLog = new MerchantReportEntry();
        expectedLog.setAmount(10);
        expectedLog.setToken(token.getTokens().get(0).getId());
        expectedReport.addToLog(expectedLog);
        assertEquals(report, expectedReport);
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
