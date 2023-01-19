package ReportManagement.model;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;


@XmlRootElement // Needed for XML serialization and deserialization
@Data // Automatic getter and setters and equals etc
@NoArgsConstructor
public class ManagerReport {
    ArrayList<ManagerReportEntry> log = new ArrayList<>();
    public void addToLog(ManagerReportEntry reportEntry) {
        log.add(reportEntry);
    }
}
