

package TokenManagement.models;

import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;
import lombok.NoArgsConstructor;
//@Author: Mila s223313
@XmlRootElement // Needed for XML serialization and deserialization
@Data // Automatic getter and setters and equals etc
@NoArgsConstructor // Needed for JSON deserialization and XML serialization and deserialization@AllArgsCo
public class TokenRequestResponse {
    private TokenList tokens;
    private String errorMessage;

    public TokenRequestResponse(String errorMessage){
        this.errorMessage = errorMessage;
    }

    public TokenRequestResponse(TokenList tokens) {
        this.tokens = tokens;
    }
}

