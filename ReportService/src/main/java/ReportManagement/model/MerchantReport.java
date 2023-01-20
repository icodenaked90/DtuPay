package ReportManagement.model;
import lombok.Data;

import java.util.ArrayList;

@Data
public class MerchantReport {
    ArrayList<MerchantReportEntry> log = new ArrayList<>();
    public void addToLog(MerchantReportEntry reportEntry) {
        log.add(reportEntry);
    }
}
