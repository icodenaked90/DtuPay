package behaviourtests;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import Payment.PaymentService;
import Payment.Token;
import TestStubs.StubAccountService;
import messaging.Event;
import messaging.MessageQueue;
import Payment.CorrelationId;
import Payment.NewPayment;

import TestStubs.StubTokenService;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import messaging.implementations.RabbitMqQueue;

import java.util.concurrent.CompletableFuture;

public class PaymentServiceSteps {
    // @Author: Jonathan (S194134)
    String customerToken, merchantId;
    int paymentAmount;

    //MessageQueue queue = mock(MessageQueue.class);
    MessageQueue queue = new RabbitMqQueue("localhost");
    PaymentService paymentService = new PaymentService(queue);
    StubTokenService tokenService = new StubTokenService(queue);
    StubAccountService accountService = new StubAccountService(queue);

    NewPayment payment, expectedPayment;
    private CompletableFuture<NewPayment> completePayment = new CompletableFuture<>();
    private CorrelationId correlationId;

    @Given("an existing customer with id {string} and token {string}")
    public void anExisitingCustomerWithIdAndToken(String arg0, String arg1) {
        Token token = new Token(arg1);
        tokenService.addToken(token, arg0);
    }
    @And("an existing merchant {string} bank id {string}")
    public void anExistingMerchantBankId(String arg0, String arg1) {
        accountService.addAccount(arg0, arg1);
    }

    @And("an existing customer {string} bankId with id {string}")
    public void anExistingCustomerBankIdWithId(String arg0, String arg1) {
        accountService.addAccount(arg0, arg1);
    }

    @Given("starting a payment from {string} to {string} for {int} kr")
    public void paymentFromToForKr(String arg0, String arg1, int arg2) {
        // Reset logs to easily find new payment in logs
        paymentService.resetPaymentLogs();

        customerToken = arg0;
        merchantId = arg1;
        paymentAmount = arg2;
    }

    @When("a {string} event is received")
    public void aEventIsReceived(String eventName) {
        payment = new NewPayment(customerToken, merchantId, paymentAmount);
        assertNull(payment.getErrorMessage());
        assertFalse(payment.isPaymentSuccesful());
        correlationId = CorrelationId.randomId();
        paymentService.handlePaymentRequested(new Event(eventName,new Object[] {payment, correlationId}));
    }

    @Then("a {string} event is sent")
    public void aEventIsSent(String eventName) {
        expectedPayment = new NewPayment(customerToken, merchantId, paymentAmount);
        expectedPayment.setPaymentSuccesful(true);
        expectedPayment.setErrorMessage("");
        var event = new Event(eventName, new Object[] {expectedPayment,correlationId});
        //verify(queue).publish(event);
        // TODO: ?
    }

    @And("the payment is successful")
    public void thePaymentIsSuccessful() {
        NewPayment completedPayment = paymentService.getPaymentLogs().get(0);
        assertTrue(completedPayment.isPaymentSuccesful());
    }

    @And("the payment is unsuccessful with message {string}")
    public void thePaymentIsUnsuccessfulWithMessage(String arg0) {
        NewPayment completedPayment = paymentService.getPaymentLogs().get(0);
        assertFalse(completedPayment.isPaymentSuccesful());
        assertEquals(completedPayment.getErrorMessage(), arg0);
    }
}
