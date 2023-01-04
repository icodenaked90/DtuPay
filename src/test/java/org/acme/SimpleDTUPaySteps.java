package org.acme;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Given;

import java.util.ArrayList;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
public class SimpleDTUPaySteps {
    String cid, mid;
    int amount;
    SimpleDTUPay dtuPay = new SimpleDTUPay();
    boolean successful;
    ArrayList<PaymentLogEntry> paymentLogEntryList;
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
        successful = dtuPay.pay(amount,cid,mid);
    }
    @Then("the payment is successful")
    public void thePaymentIsSuccessful() {
        assertTrue(successful);
    }

    @Given("a successful payment of {string} kr from customer {string} to merchant {string}")
    public void aSuccessfulPaymentOfKrFromCustomerToMerchant(String amount, String cid, String mid) {
        this.cid = cid;
        this.mid = mid;
        this.amount = Integer.parseInt(amount);
        successful = dtuPay.pay(this.amount,cid,mid);
    }

    @When("the manager asks for a list of payments")
    public void theManagerAsksForAListOfPayments() {
        paymentLogEntryList = dtuPay.getPaymentLog();
    }

    @Then("the list contains a payment where customer {string} paid {string} kr to merchant {string}")
    public void theListContainsAPaymentWhereCustomerPaidKrToMerchant(String cid, String amount, String mid) {
        int newAmount = Integer.parseInt(amount);
        boolean result = paymentLogEntryList.contains(new PaymentLogEntry(newAmount, cid, mid));
        assertTrue(result);
    }

    @When("the merchant initiates a payment for {string} kr by the customer")
    public void theMerchantInitiatesAPaymentForKrByTheCustomer(String amount) {
        int newAmount = Integer.parseInt(amount);
        successful = dtuPay.pay(newAmount,this.cid,this.mid);
    }

    @Then("the payment is not successful")
    public void thePaymentIsNotSuccessful() {
        assertFalse(successful);
    }

    //@And("an error message is returned saying {string}")
    //public void anErrorMessageIsReturnedSaying(String arg0) {
    //}
}
