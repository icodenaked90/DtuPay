package ReportManagement.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Automatic getter and setters and equals etc
@NoArgsConstructor
public class ManagerReportEntry {
    Integer amount;
    String token;
    String cid;
    String mid;
    public ManagerReportEntry(int _amount, String _token) {
        amount = _amount;
        token = _token;
    }
}
