// @Author: Jonathan (S194134)

package behaviourtests;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

import Payment.PaymentService;
import Payment.models.Token;
import TestStubs.StubAccountService;
import dtu.ws.fastmoney.BankService;
import messaging.Event;
import messaging.MessageQueue;
import Payment.models.CorrelationId;
import Payment.models.NewPayment;

import TestStubs.StubTokenService;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import messaging.implementations.RabbitMqQueue;

public class PaymentServiceSteps {

    String customerToken, merchantId;
    int paymentAmount;
    private CorrelationId correlationId;

    MessageQueue queue = new RabbitMqQueue();
    BankService bank =  mock(BankService.class);
    PaymentService paymentService = new PaymentService(queue, bank);
    StubTokenService tokenService = new StubTokenService(queue);
    StubAccountService accountService = new StubAccountService(queue);
    NewPayment payment, expectedPayment;

    // @Author: Jonathan (s194134)
    @Given("an existing customer with id {string} and token {string}")
    public void anExisitingCustomerWithIdAndToken(String arg0, String arg1) {
        Token token = new Token(arg1);
        tokenService.addToken(token, arg0);

    }
    // @Author: Jonathan (s194134)
    @And("an existing merchant {string} bank id {string}")
    public void anExistingMerchantBankId(String arg0, String arg1) {
        accountService.addAccount(arg0, arg1);
    }

    // @Author: Jonathan (s194134)
    @And("an existing customer {string} bankId with id {string}")
    public void anExistingCustomerBankIdWithId(String arg0, String arg1) {
        accountService.addAccount(arg0, arg1);
    }

    // @Author: Jonathan (s194134)
    @Given("starting a payment from {string} to {string} for {int} kr")
    public void paymentFromToForKr(String arg0, String arg1, int arg2) {
        // Reset logs to easily find new payment in logs
        paymentService.resetPaymentList();

        customerToken = arg0;
        merchantId = arg1;
        paymentAmount = arg2;

        //  Wait for all services to catch up
        try { Thread.sleep(1000); } catch (InterruptedException e) { e.printStackTrace(); }
    }

    // @Author: Jonathan (s194134)
    @When("a {string} event is received")
    public void aEventIsReceived(String eventName) {
        payment = new NewPayment(customerToken, merchantId, paymentAmount);
        assertNull(payment.getErrorMessage());
        assertFalse(payment.isPaymentSuccesful());
        correlationId = CorrelationId.randomId();
        paymentService.handlePaymentRequested(new Event(eventName,new Object[] {payment, correlationId}));
    }

    // @Author: Jonathan (s194134)
    @Then("a {string} event is sent")
    public void aEventIsSent(String eventName) {
        expectedPayment = new NewPayment(customerToken, merchantId, paymentAmount);
        expectedPayment.setPaymentSuccesful(true);
        expectedPayment.setErrorMessage("");
        var event = new Event(eventName, new Object[] {expectedPayment,correlationId});
        //verify(queue).publish(event);
        // TODO: ?
    }

    // @Author: Jonathan (s194134)
    @And("the payment is successful")
    public void thePaymentIsSuccessful() {
        NewPayment completedPayment = paymentService.getPaymentLogs().get(0);
        assertTrue(completedPayment.isPaymentSuccesful());
    }

    // @Author: Jonathan (s194134)
    @And("the payment is unsuccessful with message {string}")
    public void thePaymentIsUnsuccessfulWithMessage(String arg0) {
        NewPayment completedPayment = paymentService.getPaymentLogs().get(0);
        assertFalse(completedPayment.isPaymentSuccesful());
        assertEquals(completedPayment.getErrorMessage(), arg0);
    }
}
