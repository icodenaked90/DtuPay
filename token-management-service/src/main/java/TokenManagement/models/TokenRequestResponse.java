//TODO: Authors missing

package TokenManagement.models;

import TokenManagement.models.TokenList;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;
import lombok.NoArgsConstructor;

@XmlRootElement // Needed for XML serialization and deserialization
@Data // Automatic getter and setters and equals etc
@NoArgsConstructor // Needed for JSON deserialization and XML serialization and deserialization@AllArgsCo
public class TokenRequestResponse {
    private TokenList tokens;
    private String errorMessage;

    //TODO: should be deleted?
    public TokenList getTokens() { return tokens; }
    public boolean isError() { return errorMessage != null; }
    public String getErrorMessage() { return errorMessage; }

    public TokenRequestResponse(String errorMessage){
        this.errorMessage = errorMessage;
    }

    public TokenRequestResponse(TokenList tokens) {
        this.tokens = tokens;
    }
}

