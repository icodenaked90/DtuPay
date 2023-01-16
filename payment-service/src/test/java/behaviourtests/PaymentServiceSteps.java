package behaviourtests;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import Payment.PaymentService;
import messaging.Event;
import messaging.MessageQueue;
import Payment.CorrelationId;
import Payment.NewPayment;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class PaymentServiceSteps {
    String customerToken, merchantId;
    int paymentAmount;
    MessageQueue queue = mock(MessageQueue.class);
    PaymentService paymentService = new PaymentService(queue);
    NewPayment payment, expectedPayment;
    private CorrelationId correlationId;

    @Given("a customer with token {string}")
    public void aCustomerWithToken(String arg0) {
        customerToken = arg0;
    }

    @And("a merchant with id {string}")
    public void aMerchantWithId(String arg0) {
        merchantId = arg0;
    }
    @And("a payment amount of {int}")
    public void aPaymentAmountOf(int arg0) {
        paymentAmount = arg0;
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
        verify(queue).publish(event);
    }

    @And("the payment is successful")
    public void thePaymentIsSuccessful() {
//      TODO: This doesnt make sense. How can we make it better?
//        Or just remove maybe, it is checked above. It is always true
        assertTrue(expectedPayment.isPaymentSuccesful());
    }


}

//public class StudentIdServiceSteps {

//
//    @When("a {string} event for a student is received")
//    public void aEventForAStudentIsReceived(String eventName) {

//    }
//
//    @Then("the {string} event is sent with the same correlation id")
//    public void theEventIsSentWithTheSameCorrelationId(String eventName) {

//    }
//
//    @Then("the student gets a student id")
//    public void theStudentGetsAStudentId() {
//        assertNotNull(expected.getId());
//    }
//
//}
//
