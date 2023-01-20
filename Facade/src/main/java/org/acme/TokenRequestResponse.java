package org.acme;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.acme.models.TokenList;

import javax.xml.bind.annotation.XmlRootElement;

// @Author Mila s223313

@XmlRootElement // Needed for XML serialization and deserialization
@Data // Automatic getter and setters and equals etc
@NoArgsConstructor // Needed for JSON deserialization and XML serialization and deserialization@AllArgsCo
public class TokenRequestResponse {
    private TokenList tokens;
    private String errorMessage;

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

