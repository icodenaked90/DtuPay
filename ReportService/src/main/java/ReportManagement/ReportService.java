/*
@Author: Simon s163595
@Author: Emily s223122
...
 */

package ReportManagement;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

import ReportManagement.model.Account;
import ReportManagement.model.CustomerReport;
import messaging.Event;
import messaging.MessageQueue;

public class ReportService {

    public static final String REPORT_REQUESTED = "ReportRequested";
    public static final String REPORT_GENERATED = "ReportGenerated";
    public static final String LOG_REQUESTED = "LogRequested";
    public static final String REPORT_FINAL = "ReportFinal";
    public MessageQueue queue;
    private HashMap<String, String> userAccounts = new HashMap<String, String>();
    private Map<CorrelationId, CompletableFuture<Account>> correlations = new ConcurrentHashMap<>();


    public ReportService(MessageQueue q) {
        queue = q;
        queue.addHandler(REPORT_REQUESTED, this::handleReportRequested);
        queue.addHandler(REPORT_GENERATED, this::handleReportGenerated);
    }

    public void handleReportGenerated(Event e) {
        //if report is not null
        if (e.getArgument(0, CustomerReport.class) != null) {
            // Får Report fra Payment og sender tilbage til facaden
            var report = e.getArgument(0, CustomerReport.class);
            var correlationid = e.getArgument(1, CorrelationId.class);

            Event event = new Event(REPORT_FINAL, new Object[]{report, correlationid});
            queue.publish(event);
        }
        // Error message here

    }

    public void handleReportRequested(Event e) {
        // får ID fra facade og sender ID til Payment
        var id = e.getArgument(0, String.class);
        var correlationid = e.getArgument(1, CorrelationId.class);
        if (id != null) {
            Event event = new Event(LOG_REQUESTED, new Object[]{id, correlationid});
            queue.publish(event);
        }
        //Error message
    }
}