/*
@Author: Emily s223122
*/
package clientApp.models;

import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;
import lombok.NoArgsConstructor;

@XmlRootElement // Needed for XML serialization and deserialization
@Data // Automatic getter and setters and equals etc
@NoArgsConstructor // Needed for JSON deserialization and XML serialization and deserialization@AllArgsCo
public class ReportRequestCommand {
    public String cid;

    public ReportRequestCommand(String _cid){
        cid = _cid;
    }
}
