

package TokenManagement;

import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;
import lombok.NoArgsConstructor;
//@Mila s223313
@XmlRootElement // Needed for XML serialization and deserialization
@Data // Automatic getter and setters and equals etc
@NoArgsConstructor // Needed for JSON deserialization and XML serialization and deserialization@AllArgsCo
public class TokenRequestCommand {
    public String cid;
    public Integer amount;

    public TokenRequestCommand(String _cid, Integer _amount){
        cid = _cid;
        amount = _amount;
    }
}
