/*
@Author: Simon s163595
@Author: Emily s223122
@Author: Adin s164432
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
    //@Author: Emily s223122
    public void handleCustomerLogRequested(Event e) {
        String cid = e.getArgument(0, String.class);
        CorrelationId corId = e.getArgument(1, CorrelationId.class);
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
        TransactionLog full = e.getArgument(0, TransactionLog.class);
        CorrelationId corId = e.getArgument(1, CorrelationId.class);
        AccountType type = idToTypeMap.get(full.getId());

        if (type == AccountType.CUSTOMER) {
            CustomerReport report = new CustomerReport();
            for (Transaction t : full.getLog()) {
                if (full.getId().equals(t.getCustomerId())) {
                    CustomerReportEntry entry = new CustomerReportEntry();
                    entry.setAmount(t.getAmount());
                    entry.setToken(t.getCustomerToken());
                    entry.setMid(t.getMerchantId());
                    report.addToLog(entry);
                }
            }
            Event event = new Event(CUSTOMER_LOG_GENERATED, new Object[]{report, corId});
            queue.publish(event);
            return;
        }
        if (type == AccountType.MERCHANT) {
            MerchantReport report = new MerchantReport();
            for (Transaction t : full.getLog()) {
                if (full.getId().equals(t.getMerchantId())) {
                    MerchantReportEntry entry = new MerchantReportEntry();
                    entry.setAmount(t.getAmount());
                    entry.setToken(t.getCustomerToken());
                    report.addToLog(entry);
                }
            }
            Event event = new Event(MERCHANT_LOG_GENERATED, new Object[]{report, corId});
            queue.publish(event);
            return;
        }
        ManagerReport report = new ManagerReport();
        for (Transaction t : full.getLog()) {
            if (full.getId().equals(t.getCustomerId())) {
                ManagerReportEntry entry = new ManagerReportEntry();
                entry.setAmount(t.getAmount());
                entry.setToken(t.getCustomerToken());
                entry.setMid(t.getMerchantId());
                entry.setCid(t.getCustomerId());
                report.addToLog(entry);
            }
        }
        Event event = new Event(MANAGER_LOG_GENERATED, new Object[]{report, corId});
        queue.publish(event);
    }
}