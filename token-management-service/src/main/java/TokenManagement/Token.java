// Token class for generating tokens and validating them
// Built when mob programming
// @Author: Jonathan Zørn (S194134)
// @Author: Adin (s164432)
// @Author: Mila (s223313)
// @Author: Simon Philipsen (S163595)
package TokenManagement;

import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@XmlRootElement // Needed for XML serialization and deserialization
@Data // Automatic getter and setters and equals etc
@NoArgsConstructor // Needed for JSON deserialization and XML serialization and deserialization@AllArgsCo
public class Token {
    String id;

    private static int tokenLength = 128;

    // function to generate a random string of length n
    public static Token generateToken() {
        while (true) {// choose a Character random from this String
            String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                    + "0123456789"
                    + "abcdefghijklmnopqrstuvxyz";

            // create StringBuffer size of AlphaNumericString
            StringBuilder sb = new StringBuilder(tokenLength);
            for (int i = 0; i < tokenLength; i++) {
                // generate a random number between 0 to n
                int index = (int) (AlphaNumericString.length() * Math.random());
                // add Character one by one in end of sb
                sb.append(AlphaNumericString
                        .charAt(index));
            }

            // Check for we have not generated a duplicate token
            Token token = new Token();
            token.id = sb.toString();
            return token;
        }
    }
    public ArrayList<String> currentValidTokens = new ArrayList<String>();

    public boolean validateToken(String token) {
        for (String tok : currentValidTokens) {
            if (token.equals(tok)) {
                return true;
            }
        }
        return false;
    }

    public boolean consumeToken(String token) {
        if (validateToken(token)) {
            currentValidTokens.remove(token);
            return true;
        }
        return false;
    }
}