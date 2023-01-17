
/*
@Author: Simon s163595
@Author: Emily s223122
...
 */
package behaviourtests;



import ReportManagement.CorrelationId;
import ReportManagement.ReportService;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import messaging.Event;
import messaging.MessageQueue;

import static ReportManagement.ReportService.LOG_REQUESTED;
import static ReportManagement.ReportService.REPORT_REQUESTED;
import static org.mockito.Mockito.*;

public class ReportStepdef {
    MessageQueue queue = mock(MessageQueue.class);
    ReportService service = new ReportService(queue);

    Event e;

    CorrelationId corId;

    String userId;

    @Given("User has an ID")
    public void userHasAnID() {
        userId = "aa";
    }

    @And("A user requests a report")
    public void aUserRequestsAReport() {
        corId = CorrelationId.randomId();
        e = new Event(REPORT_REQUESTED, new Object[] {userId , corId});
        service.queue.publish(e);
    }



    @When("a {string} event is received")
    public void aEventIsReceived(String arg0) {
        e = new Event(arg0, new Object[] { userId, corId });
        System.out.println("See me");
        System.out.println(arg0 + "   " + userId + "   " + corId);
        verify(service.queue).publish(e);

    }


    @Then("the event is handled")
    public void theEventIsHandled() {
        service.handleReportRequested(e);
    }

    @Then("a {string} event is sent")
    public void aEventIsSent(String arg0) {
        e = new Event(arg0, new Object[] { userId, corId });
        verify(service.queue).publish(e);
    }



}
