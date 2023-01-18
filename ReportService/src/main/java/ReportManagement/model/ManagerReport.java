package ReportManagement.model;
import lombok.Data;

import java.util.ArrayList;

@Data
public class ManagerReport {
    ArrayList<ManagerReportEntry> log = new ArrayList<>();
    public void addToLog(ManagerReportEntry reportEntry) {
        log.add(reportEntry);
    }
}
