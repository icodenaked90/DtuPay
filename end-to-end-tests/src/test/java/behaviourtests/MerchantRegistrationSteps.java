package behaviourtests;

import clientApp.CustomerAppService;
import clientApp.MerchantAppService;
import clientApp.models.Account;
import dtu.ws.fastmoney.BankService;
import dtu.ws.fastmoney.BankServiceException_Exception;
import dtu.ws.fastmoney.BankServiceService;
import dtu.ws.fastmoney.User;
import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.math.BigDecimal;

import static org.junit.Assert.*;
import static org.wildfly.common.Assert.assertTrue;
/*
public class MerchantRegistrationSteps {
    private Account merchant;
    private User bankMerchant = new User();
    private String mid = "";
    private String mAccount;
    private String response = "";
    private MerchantAppService merchantApp= new MerchantAppService();
    private BankService bank = new BankServiceService().getBankServicePort();

    @Given("an unregistered merchant")
    public void anUnregisteredMerchant() {
        // Customer has valid bank account
        bankMerchant.setFirstName("Test");
        bankMerchant.setLastName("Customer");
        bankMerchant.setCprNumber("010170-1996");
        try {
            mAccount = bank.createAccountWithBalance(bankMerchant, BigDecimal.valueOf(2000));
        } catch (BankServiceException_Exception e) {
            fail("Invalid customer bank account.");
        }
        merchant = new Account("Test Customer", "010170-1996", mAccount);
    }

    @When("the merchant is being registered in DTUPay")
    public void theMerchantIsBeingRegisteredInDTUPay() {
        // Create registered customer on DTU Pay
        response = merchantApp.register(merchant);
        mid = response;
    }

    @Then("the merchant receives an DTUPay id")
    public void theMerchantReceivesAnDTUPayId() {
        assertNotEquals("fail", mid);
    }

    @When("the merchant is being deregistered in DTUPay")
    public void theMerchantIsBeingDeregisteredInDTUPay() {
        response = merchantApp.deregister(cid);
    }

    @Then("the merchant receives an error message")
    public void theMerchantReceivesAnErrorMessage() {
        assertEquals("fail", response);
    }

    @Given("a registered merchant")
    public void aRegisteredMerchant() {
        // Customer has valid bank account
        bankMerchant.setFirstName("Test");
        bankMerchant.setLastName("Customer");
        bankMerchant.setCprNumber("010170-1996");
        try {
            mAccount = bank.createAccountWithBalance(bankMerchant, BigDecimal.valueOf(2000));
        } catch (BankServiceException_Exception e) {
            fail("Invalid customer bank account.");
        }
        // Create registered customer on DTU Pay
        merchant = new Account("Test Customer", "010170-1996", cAccount);
        response = merchantApp.register(merchant);
        mid = response;
        assertNotEquals("fail", response);
    }

    @Then("the merchant is deregistered")
    public void theMerchantIsDeregistered() {
        assertEquals("ok", response);
    }
}

 */
