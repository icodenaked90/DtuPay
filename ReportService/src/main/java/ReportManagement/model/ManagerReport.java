package ReportManagement.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;


@Data // Automatic getter and setters and equals etc
@NoArgsConstructor
public class ManagerReport {
    ArrayList<ManagerReportEntry> log = new ArrayList<>();
    public void addToLog(ManagerReportEntry reportEntry) {
        log.add(reportEntry);
    }
}
