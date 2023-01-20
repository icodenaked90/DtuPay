// Token class for generating tokens and validating them
// Built when mob programming
// @Author: Jonathan Zørn (S194134)
// @Author: Adin (s164432)
// @Author: Mila (s223313)
// @Author: Simon Philipsen (S163595)

package TokenManagement.models;

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

    public static Token generateToken() {
        while (true) {
            String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                    + "0123456789"
                    + "abcdefghijklmnopqrstuvxyz";

            StringBuilder sb = new StringBuilder(tokenLength);
            for (int i = 0; i < tokenLength; i++) {
                int index = (int) (AlphaNumericString.length() * Math.random());
                sb.append(AlphaNumericString
                        .charAt(index));
            }

            Token token = new Token();
            token.id = sb.toString();
            return token;
        }
    }
}
