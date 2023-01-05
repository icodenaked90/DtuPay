package dtuPayService;

import dtu.ws.fastmoney.*;
import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Given;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.ArrayList;
import org.acme.PaymentLogEntry;
import org.acme.ResponseStatus;

import static org.junit.Assert.*;

public class SimpleDTUPaySteps {
    User customer = new User();
    User merchant = new User();
    String cid, mid;
    int amount;
    SimpleDTUPayService dtuPay = new SimpleDTUPayService();
    BankService bank = new BankServiceService().getBankServicePort();
    ResponseStatus responseStatus;
    PaymentLogEntry[] paymentLogEntryList;
    @Given("a customer with id {string}")
    public void aCustomerWithId(String cid) {
        this.cid = cid;
    }
    @Given("a merchant with id {string}")
    public void aMerchantWithId(String mid) {
        this.mid = mid;
    }
    @When("the merchant initiates a payment for {int} kr by the customer")
    public void theMerchantInitiatesAPaymentForKrByTheCustomer(int amount) {
        responseStatus = dtuPay.pay(amount,cid,mid);
    }
    @Then("the payment is successful")
    public void thePaymentIsSuccessful() {
        assertTrue(responseStatus.status);
    }

    @Given("a successful payment of {string} kr from customer {string} to merchant {string}")
    public void aSuccessfulPaymentOfKrFromCustomerToMerchant(String amount, String cid, String mid) {
        this.cid = cid;
        this.mid = mid;
        this.amount = Integer.parseInt(amount);
        responseStatus = dtuPay.pay(this.amount,cid,mid);
    }

    @When("the manager asks for a list of payments")
    public void theManagerAsksForAListOfPayments() {
        paymentLogEntryList = dtuPay.getPaymentLog();

    }

    @Then("the list contains a payment where customer {string} paid {string} kr to merchant {string}")
    public void theListContainsAPaymentWhereCustomerPaidKrToMerchant(String cid, String amount, String mid) {
        int newAmount = Integer.parseInt(amount);
        PaymentLogEntry goal = new PaymentLogEntry(newAmount, cid, mid);

        for (PaymentLogEntry payment:paymentLogEntryList) {
            if(goal.equals(payment)){
                assertTrue(true);
                return;
            }
        }
        fail();
    }

    @When("the merchant initiates a payment for {string} kr by the customer")
    public void theMerchantInitiatesAPaymentForKrByTheCustomer(String amount) {
        int newAmount = Integer.parseInt(amount);
        responseStatus = dtuPay.pay(newAmount,this.cid,this.mid);
    }

    @Then("the payment is not successful")
    public void thePaymentIsNotSuccessful() {
        assertFalse(responseStatus.status);
    }

    @And("an error message is returned saying {string}")
    public void anErrorMessageIsReturnedSaying(String errorMessage) {
        assertEquals(errorMessage, responseStatus.errorMessage);
    }

    @Given("a customer with a bank account with balance {int}")
    public void aCustomerWithABankAccountWithBalance(int arg0) {
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setCprNumber("010170-1099");
        try {
            cid = bank.createAccountWithBalance(customer, BigDecimal.valueOf(arg0));
        } catch (BankServiceException_Exception e) {
            fail("Failed to create customer account.");
        }
    }

    @And("that the customer is registered with DTU Pay")
    public void thatTheCustomerIsRegisteredWithDTUPay() {
        // TODO
    }

    @Given("a merchant with a bank account with balance {int}")
    public void aMerchantWithABankAccountWithBalance(int arg0) {
        merchant.setFirstName("Mark");
        merchant.setLastName("Twain");
        merchant.setCprNumber("121278-2889");
        try {
            mid = bank.createAccountWithBalance(merchant, BigDecimal.valueOf(arg0));
        } catch (BankServiceException_Exception e) {
            fail("Failed to create merchant account.");
        }
    }

    @And("that the merchant is registered with DTU Pay")
    public void thatTheMerchantIsRegisteredWithDTUPay() {
        // TODO
    }

    @And("the balance of the customer at the bank is {int} kr")
    public void theBalanceOfTheCustomerAtTheBankIsKr(int arg0) {
        try {
            assertEquals(BigDecimal.valueOf(arg0), bank.getAccount(cid).getBalance());
        } catch (BankServiceException_Exception e) {
            fail("Failed to get balance of customer account.");
        }
    }

    @And("the balance of the merchant at the bank is {int} kr")
    public void theBalanceOfTheMerchantAtTheBankIsKr(int arg0) {
        try {
            assertEquals(BigDecimal.valueOf(arg0), bank.getAccount(mid).getBalance());
        } catch (BankServiceException_Exception e) {
            fail("Failed to get balance of merchant account.");
        }
    }

    @After()
    public void Cleanup()
    {
        if (cid != null) {
            try {
                bank.retireAccount(cid);
                cid = null;
            } catch (BankServiceException_Exception e) {

            }
        }
        if (mid != null) {
            try {
                bank.retireAccount(mid);
                mid = null;
            } catch (BankServiceException_Exception e) {

            }
        }
    }
}
