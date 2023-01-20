package ReportManagement.model;
import lombok.Data;

import java.util.ArrayList;
//@Author: Emily s223122
@Data
public class MerchantReport {
    ArrayList<MerchantReportEntry> log = new ArrayList<>();
    public void addToLog(MerchantReportEntry reportEntry) {
        log.add(reportEntry);
    }
}
