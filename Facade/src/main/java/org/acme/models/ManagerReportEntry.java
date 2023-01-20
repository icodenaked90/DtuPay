package org.acme.models;

import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;
import lombok.NoArgsConstructor;
//@Author: Emily s2231322
@XmlRootElement // Needed for XML serialization and deserialization
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