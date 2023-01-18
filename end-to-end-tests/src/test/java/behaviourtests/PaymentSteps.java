/*
@Author Mila (s223313)
@Author Adin (s164432)
*/

package behaviourtests;
import clientApp.CustomerAppService;
import clientApp.MerchantAppService;
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
    private Account merchant;
    private User bankCustomer = new User();
    private String cid = "not a valid cid";
    private String mid = "not a valid mid";
    private String cAccount;
    private String mAccount;
    private CustomerAppService customerService = new CustomerAppService();
    private MerchantAppService merchantService = new MerchantAppService();
    private BankService bank = new BankServiceService().getBankServicePort();
    private TokenList ownedTokens;
    private TokenList receivedTokens;
    private String errorMessage = "";


    @Given("the registered customer with {int} tokens with {int} balance")
    public void theRegisteredCustomerWithTokensWithBalance(int arg0, int arg1) {
        bankCustomer.setFirstName("Abel");
        bankCustomer.setLastName("Shawn");
        bankCustomer.setCprNumber("121212-4444");
        try {
            cAccount = bank.createAccountWithBalance(bankCustomer, BigDecimal.valueOf(arg1));
        } catch (BankServiceException_Exception e) {
            fail("Invalid bank account.");
        }

        // Create registered customer on DTU Pay
        customer = new Account("Abel Shawn", "121212-4444", cAccount);
        cid = customerService.register(customer);
        assertNotEquals("fail", cid);

        // Get initial tokens
        ownedTokens = new TokenList(new ArrayList<Token>());

        try {
            ownedTokens = customerService.getTokens(cid, arg0);
        } catch (Exception e) {
            fail(e.getMessage());
        }

    }

    @And("a registered merchant with {int} balance")
    public void aRegisteredMerchantWithBalance(int arg0) {
        bankCustomer.setFirstName("Moobi");
        bankCustomer.setLastName("Lee");
        bankCustomer.setCprNumber("030303-3333");
        System.out.println("BANK CUSTOMER:"+bankCustomer.toString());
        try {
            mAccount = bank.createAccountWithBalance(bankCustomer, BigDecimal.valueOf(arg0));
        } catch (BankServiceException_Exception e) {
            fail("Invalid bank account.");
        }
        // Create registered merchant on DTU Pay
        merchant = new Account("Moobi Lee", "030303-3333", mAccount);
        mid = merchantService.register(merchant);
        assertNotEquals("fail", mid);
    }

    @When("the merchant requests a payment for {int}")
    public void theMerchantRequestsAPaymentFor(int arg0) {
        //TODO: update errormessage
        merchantService.pay(arg0,cid,mid);
    }

    @Then("the customer has {int} tokens")
    public void theCustomerHasTokens(int arg0) {
        assertEquals(arg0, ownedTokens.getTokens().size());
    }

    @And("the customers bank balance  is {int}")
    public void theCustomersBankBalanceIs(int arg0) {
        try {
            assertEquals(arg0, bank.getAccount(cAccount).getBalance());
        } catch (BankServiceException_Exception e) {
            fail("Invalid bank account.");
        }
    }

    @And("the merchant bank balance is {int}")
    public void theMerchantBankBalanceIs(int arg0) {
        try {
            assertEquals(arg0, bank.getAccount(mAccount).getBalance());
        } catch (BankServiceException_Exception e) {
            fail("Invalid bank account.");
        }
    }


    @Then("the merchant receives the error message {string}")
    public void theMerchantReceivesTheErrorMessage(String arg0)  {
        assertEquals(arg0, errorMessage);
    }


    @Given("the unregistered customer with {int} tokens")
    public void theUnregisteredCustomerWithTokens(int arg0) {
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
        cid ="faker";

        // Get initial tokens
        ownedTokens = new TokenList(new ArrayList<Token>());

        try {
            ownedTokens = customerService.getTokens(cid, arg0);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @And("a unregistered merchant")
    public void aUnregisteredMerchant() {
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
        mid = "faker";
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
