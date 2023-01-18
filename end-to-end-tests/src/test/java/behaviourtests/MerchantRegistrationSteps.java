/*
@Author: Emily s223122
@Author: Simon s163595
@Author: Mila s223313
 */

package behaviourtests;
import clientApp.CustomerAppService;
import dtu.ws.fastmoney.*;
import clientApp.MerchantAppService;
import clientApp.models.Account;

import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.Assert.*;
import java.math.BigDecimal;

public class MerchantRegistrationSteps {
    private Account merchant;
    User bankCustomer = new User();
    private String mid = "not a valid mid";
    private String mAccount;
    private String deregisterReply = "";
    private MerchantAppService merchantService = new MerchantAppService();
    BankService bank = new BankServiceService().getBankServicePort();

    @Given("an unregistered merchant")
    public void anUnregisteredMerchant() {
        bankCustomer.setFirstName("Moobi");
        bankCustomer.setLastName("Lee");
        bankCustomer.setCprNumber("030303-3412");
        System.out.println("BANK CUSTOMER:"+bankCustomer.toString());
        try {
            mAccount = bank.createAccountWithBalance(bankCustomer, BigDecimal.valueOf(30000));
        } catch (BankServiceException_Exception e) {
            fail("Invalid bank account.");
        }
        merchant = new Account("Moobi Lee", "030303-3412", mAccount);
    }

    @Given("a registered merchant")
    public void aRegisteredMerchant() {
        // Merchant has valid bank account
        bankCustomer.setFirstName("Moobi");
        bankCustomer.setLastName("Lee");
        bankCustomer.setCprNumber("030303-3433");
        System.out.println("BANK CUSTOMER:"+bankCustomer.toString());
        try {
            mAccount = bank.createAccountWithBalance(bankCustomer, BigDecimal.valueOf(30000));
        } catch (BankServiceException_Exception e) {
            fail("Invalid bank account.");
        }
        // Create registered merchant on DTU Pay
        merchant = new Account("Moobi Lee", "030303-3412", mAccount);
        mid = merchantService.register(merchant);
        assertNotEquals("fail", mid);
    }

    @When("the merchant is being registered in DTUPay")
    public void theMerchantIsBeingRegisteredInDTUPay() {
        mid = merchantService.register(merchant);
        System.out.println("MID: " + mid);
    }

    @Then("the merchant receives a DTUPay id")
    public void theMerchantReceivesADTUPayId() {
        assertNotEquals("fail", mid);
    }

    @When("the merchant is being deregistered in DTUPay")
    public void theMerchantIsBeingDeregisteredInDTUPay() {
        deregisterReply = merchantService.deregister(mid);
    }

    @Then("the merchant receives an error message")
    public void theMerchantReceivesAnErrorMessage() {
        assertNotEquals("OK", deregisterReply);
    }

    @Then("the merchant is deregistered")
    public void theMerchantIsDeregistered() {
        assertEquals("OK", deregisterReply);
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