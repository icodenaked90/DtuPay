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

import ReportManagement.model.*;
import messaging.Event;
import messaging.MessageQueue;

public class ReportService implements IReportService{

    public MessageQueue queue;
    private HashMap<String, AccountType> idToTypeMap = new HashMap<String, AccountType>();
    private Map<CorrelationId, CompletableFuture<String>> correlations = new ConcurrentHashMap<>();


    public ReportService(MessageQueue q) {
        queue = q;
        queue.addHandler(CUSTOMER_LOG_REQUESTED, this::handleCustomerLogRequested);
        queue.addHandler(MERCHANT_LOG_REQUESTED, this::handleMerchantLogRequested);
        queue.addHandler(MANAGER_LOG_REQUESTED, this::handleManagerLogRequested);
        queue.addHandler(FULL_LOG_GENERATED, this::handleFullLogGenerated);
    }

    public void handleCustomerLogRequested(Event e) {
        String cid = e.getArgument(0, String.class);
        CorrelationId corId = e.getArgument(0, CorrelationId.class);
        idToTypeMap.put(cid, AccountType.CUSTOMER);
        Event event = new Event(FULL_LOG_REQUESTED, new Object[]{cid, corId});
        queue.publish(event);
    }

    public void handleMerchantLogRequested(Event e) {
        String mid = e.getArgument(0, String.class);
        CorrelationId corId = e.getArgument(1, CorrelationId.class);
        idToTypeMap.put(mid, AccountType.MERCHANT);
        Event event = new Event(FULL_LOG_REQUESTED, new Object[]{mid, corId});
        queue.publish(event);
    }

    public void handleManagerLogRequested(Event e) {
        String maid = e.getArgument(0, String.class);
        CorrelationId corId = e.getArgument(1, CorrelationId.class);
        idToTypeMap.put(maid, AccountType.MANAGER);
        Event event = new Event(FULL_LOG_REQUESTED, new Object[]{maid, corId});
        queue.publish(event);
    }

    public void handleFullLogGenerated(Event e) {
        String id = e.getArgument(0, String.class);
        CorrelationId corId = e.getArgument(1, CorrelationId.class);
        AccountType type = idToTypeMap.get(id);

        if (type == AccountType.CUSTOMER){
            CustomerReport report = null;
            Event event = new Event(CUSTOMER_LOG_GENERATED, new Object[]{report, corId});
            queue.publish(event);
        }
        if (type == AccountType.MERCHANT){
            MerchantReport report = null;
            Event event = new Event(MERCHANT_LOG_GENERATED, new Object[]{report, corId});
            queue.publish(event);
        }
        if (type == AccountType.MANAGER){
            ManagerReport report = new ManagerReport();
            var log = new ManagerReportEntry();
            log.setAmount(10);
            log.setToken("aaaa");
            log.setCid("cad");
            log.setMid("gfd");
            Event event = new Event(MANAGER_LOG_GENERATED, new Object[]{report, corId});
            queue.publish(event);
        }
    }
}