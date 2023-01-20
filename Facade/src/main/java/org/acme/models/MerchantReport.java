package org.acme.models;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
//@Author: Emily s2231322
@XmlRootElement // Needed for XML serialization and deserialization
@Data // Automatic getter and setters and equals etc
@NoArgsConstructor
public class MerchantReport {
    ArrayList<MerchantReportEntry> log = new ArrayList<>();
    public void addToLog(MerchantReportEntry reportEntry) {
        log.add(reportEntry);
    }
}
