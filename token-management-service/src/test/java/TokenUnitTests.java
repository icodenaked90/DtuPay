// @Author: Adin (s164432)

import TokenManagement.models.Token;
import TokenManagement.TokenService;
import messaging.MessageQueue;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class TokenUnitTests {

    @Test
    public void checkIfTokenHasCorrectSize() {
        Token token = Token.generateToken();
        assertEquals(token.getId().length(),128);
    }
    @Test
    public void checkIfTokenOnlyContainsLettersAndNumbers() {
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        Token token = Token.generateToken();
        int success = 0;

        for(int i = 0; i < token.getId().length();i++){
            for(int j = 0; j < AlphaNumericString.length(); j++){
                if(token.getId().charAt(i) == AlphaNumericString.charAt(j)){
                    success++;
                }
            }

        }
        assertEquals(128, success);
    }
    @Test
    public void checkIfUnusedAmountReturnsCorrectSize(){
        MessageQueue queue = mock(MessageQueue.class);
        String cid1 = "cake";
        String cid2 = "miles";
        String cid3 = "nugga";
        TokenService ts = new TokenService(queue);
        ts.addTokensToAccount( cid1,4);
        assertEquals(ts.unusedAmount(cid1),4);
        assertEquals(ts.unusedAmount(cid2),0);
        assertEquals(ts.unusedAmount(cid3),0);
        ts.addTokensToAccount( cid3,2);
        ts.addTokensToAccount( cid1,1);
        assertEquals(ts.unusedAmount(cid1),5);
        assertEquals(ts.unusedAmount(cid2),0);
        assertEquals(ts.unusedAmount(cid3),2);
    }
}
