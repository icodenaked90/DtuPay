package org.acme.models;
import lombok.Data;

@Data
public class ManagerReportEntry {
    Integer amount;
    String token;

    public ManagerReportEntry(int _amount, String _token) {
        amount = _amount;
        token = _token;
    }
}
