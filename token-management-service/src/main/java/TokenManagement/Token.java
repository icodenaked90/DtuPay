// Token class for generating tokens and validating them
// Built when mob programming
// @Author: Jonathan Zørn (S194134)
// @Author: Adin (s164432)
// @Author: Mila (s223313)
// @Author: Simon Philipsen (S163595)
package TokenManagement;

import lombok.Data;

@Data
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
}