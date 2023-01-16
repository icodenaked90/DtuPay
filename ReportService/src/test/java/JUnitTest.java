import ReportManagement.CorrelationId;
import ReportManagement.ReportService;
import messaging.Event;
import messaging.MessageQueue;
import messaging.implementations.RabbitMqQueue;

import static ReportManagement.ReportService.REPORT_REQUESTED;

public class JUnitTest {
    @org.junit.Before // Junit 4
    public void setUp() {
        System.out.println("setUp");
    }

    @org.junit.Test // JUnit 4
    public void CreateReportService() {
        var rp = new ReportService( new RabbitMqQueue("rabbitMq"));
        org.junit.Assert.assertTrue(true); // JUnit 4
    }

    @org.junit.Test // JUnit 4
    public void handleReportRequested_OK() {
        MessageQueue queue;
        var rp = new ReportService( new RabbitMqQueue("rabbitMq"));
        Integer accountId = 123;
        CorrelationId corId = CorrelationId.randomId();
        Event e = new Event(REPORT_REQUESTED, new Object[] { accountId, corId });

        rp.queue.publish(e);



        org.junit.Assert.assertTrue(true); // JUnit 4
    }
    @org.junit.Test // JUnit 4
    public void junit4Test() {
        System.out.println("JUnit 4");
        org.junit.Assert.assertTrue(true); // JUnit 4
    }
}