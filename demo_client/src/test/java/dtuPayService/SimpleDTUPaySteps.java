package dtuPayService;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Given;
import java.util.Arrays;
import java.util.ArrayList;
import org.acme.PaymentLogEntry;
import org.acme.ResponseStatus;

import static org.junit.Assert.*;

public class SimpleDTUPaySteps {
    String cid, mid;
    int amount;
    SimpleDTUPayService dtuPay = new SimpleDTUPayService();
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
}
