
package behaviourtests;
import clientApp.CustomerAppService;
import clientApp.models.Account;

import clientApp.models.Token;
import clientApp.models.TokenList;
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
import java.util.List;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class PaymentSteps {
    private Account customer;
    private User bankCustomer = new User();
    private String cid = "not a valid cid";
    private String cAccount;
    private CustomerAppService customerService = new CustomerAppService();
    private BankService bank = new BankServiceService().getBankServicePort();
    private TokenList ownedTokens;
    private TokenList receivedTokens;
    private String errorMessage = "";

    @Given("the registered customer with {int} tokens")
    public void theRegisteredCustomerWithTokens(int arg0) {
        // Create valid bank account
        bankCustomer.setFirstName("Abel");
        bankCustomer.setLastName("Shawn");
        bankCustomer.setCprNumber("121212-4444");
        try {
            cAccount = bank.createAccountWithBalance(bankCustomer, BigDecimal.valueOf(2000));
        } catch (BankServiceException_Exception e) {
            fail("Invalid bank account.");
        }

        // Create registered customer on DTU Pay
        customer = new Account("Abel Shawn", "121212-4444", cAccount);
        cid = customerService.register(customer);
        assertNotEquals("fail", cid);

        // Get initial tokens
        ownedTokens = new TokenList(new ArrayList<Token>());
        if (arg0 > 0) {
            try
            {
                ownedTokens = customerService.getTokens(cid, arg0);
            }
            catch (Exception e)
            {
                fail(e.getMessage());
            }
        }
    }

    @And("a registered merchant")
    public void aRegisteredMerchant() {
        // Merchant has valid bank account
        bankCustomer.setFirstName("Moobi");
        bankCustomer.setLastName("Lee");
        bankCustomer.setCprNumber("030303-3333");
        System.out.println("BANK CUSTOMER:"+bankCustomer.toString());
        try {
            mAccount = bank.createAccountWithBalance(bankCustomer, BigDecimal.valueOf(30000));
        } catch (BankServiceException_Exception e) {
            fail("Invalid bank account.");
        }
        // Create registered merchant on DTU Pay
        merchant = new Account("Moobi Lee", "030303-3333", mAccount);
        mid = merchantService.register(merchant);
        assertNotEquals("fail", mid);
    }


    @When("the merchant requests a payment")
    public void theMerchantRequestsAPayment() {
    }


    @And("the customer bank account gets validated")
    public void theCustomerBankAccountGetsValidated() {
    }


    @And("the merchant bank account gets validated")
    public void theMerchantBankAccountGetsValidated() {
    }

    @Then("the customer owns {int} tokens")
    public void theCustomerOwnsTokens(int arg0) {
        assertEquals(arg0, ownedTokens.getTokens().size());
    }


    @And("the customer pays the amount")
    public void theCustomerPaysTheAmount() {
    }


    @And("the merchant receives the amount")
    public void theMerchantReceivesTheAmount() {
    }
}
