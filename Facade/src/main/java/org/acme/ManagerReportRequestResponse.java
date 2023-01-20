package org.acme;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.acme.models.ManagerReport;

import javax.xml.bind.annotation.XmlRootElement;
//@Author: Emily s2231322
@XmlRootElement // Needed for XML serialization and deserialization
@Data // Automatic getter and setters and equals etc
@NoArgsConstructor // Needed for JSON deserialization and XML serialization and deserialization@AllArgsCo
public class ManagerReportRequestResponse {
    private ManagerReport report;
    private String errorMessage;

    public ManagerReport getReport() { return report; }
    public boolean isError() { return errorMessage != null; }
    public String getErrorMessage() { return errorMessage; }

    public ManagerReportRequestResponse(String errorMessage){
        this.errorMessage = errorMessage;
    }

    public ManagerReportRequestResponse(ManagerReport report) {
        this.report = report;
    }
}

