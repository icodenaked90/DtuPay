/* @Author: Mila (s223313)
   @Author: ...
   @Author: ...
   @Author: ...
 */

package TokenManagement;

import java.util.Map;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

import messaging.Event;
import messaging.MessageQueue;

/*
Image SQL table

Tokens
ID      Valid   AccountId
2       True    Bob
3       False   Bob
4       False   Mike
6       True    Mike

SELECT * from Tokens WHERE AccountId = "Bob"
SELECT * from Tokens WHERE AccountId = "Bob" AND Valid = true
SELECT * from Tokens WHERE AccountId = "Bob" AND Valid = false
*/
// @Author: Adin (s164432)
public class TokenService {
    public static final String TOKEN_GENERATION_REQUESTED = "TokenGenerationRequested";
    public static final String TOKEN_GENERATION_COMPLETED = "TokenGenerationCompleted";
    public static final String TOKEN_VALIDATION_REQUESTED = "TokenValidationRequested";
    public static final String TOKEN_VALIDATION_COMPLETED = "TokenValidationCompleted";
    // Map from Token to AccountId
    private Map<Token, String> tokens = new ConcurrentHashMap<>();  // SQL database table mapping Token to Account ID
    // Map from account id to unused tokens
    private Map<Token, String> unusedTokens = new ConcurrentHashMap<>();
    // Map from account id to used tokens (for reporting purposes)
    private Map<Token, String> usedTokens = new ConcurrentHashMap<>();
    private MessageQueue queue;
    public TokenService(MessageQueue q) {
        queue = q;
        queue.addHandler(TOKEN_GENERATION_REQUESTED, this::handleTokenGenerationRequested);

        queue.addHandler(TOKEN_VALIDATION_REQUESTED, this::handleTokenValidationRequested);

    }


    /*
    public void handleCustomerValidationCompleted(Event e) {
        var customerOk = e.getArgument(0, Boolean.class);
        var eventCorrelationId = e.getArgument(1, CorrelationId.class);
        customerValidationRequested.get(eventCorrelationId).complete(customerOk);
    }*/

    public void handleTokenGenerationRequested(Event e) {
        var command = e.getArgument(0, TokenRequestCommand.class);
        var eventCorrelationId = e.getArgument(2, CorrelationId.class);
        String accountId = command.cid;
        int numberOfTokens = command.amount;
        // Send event CustomerValidationRequested to AccountManagementService with accountId and correlationid
        // Account management service responds with CustomerValidationCompleted event with bool whether account is ok (exists and is customer) and the correlationId
/*
        var correlationId = CorrelationId.randomId();
        customerValidationRequested.put(correlationId, new CompletableFuture<>());
        e = new Event("CustomerValidationRequested", new Object[]{accountId, correlationId});
        queue.publish(e);
        Boolean customerOk = customerValidationRequested.get(correlationId).join();
        if (!customerOk) {
            e = new Event("TokenGenerationFailed", new Object[]{"Invalid account id", eventCorrelationId});
            queue.publish(e);
            return;
        }
*/
        // Validate token number
        if (numberOfTokens < 1 || numberOfTokens > 5) {
            e = new Event(TOKEN_GENERATION_COMPLETED, new Object[]{new ArrayList<Token>(), eventCorrelationId});
            queue.publish(e);
            return;
        }

        // Validate generation request
        if (unusedAmount(accountId) > 1) {
            e = new Event(TOKEN_GENERATION_COMPLETED, new Object[]{new ArrayList<Token>(), eventCorrelationId});
            queue.publish(e);
            return;
        }
        // Create tokens for account
        ArrayList<Token> accountNewTokens = new ArrayList<Token>();
        for (int i = 0; i < numberOfTokens; i++) {
            while(true) {
                Token token = Token.generateToken();
                if (unusedTokens.containsValue(token) && usedTokens.containsValue(token))
                    continue;
                else {
                    unusedTokens.put(token,accountId);
                    accountNewTokens.add(token);
                    break;
                }
            }
        }
        // Return new tokens
        e = new Event(TOKEN_GENERATION_COMPLETED, new Object[]{new TokenList(accountNewTokens), eventCorrelationId});
        queue.publish(e);
    }



    /// TESTING ///


/*
    public void handleTokenGenerationFailed(Event e) {
        var errorMessage = e.getArgument(0, String.class);
        var eventCorrelationId = e.getArgument(1, CorrelationId.class);
        tokenGenerationCorrelation.get(eventCorrelationId).complete(null);
    }

    public void handleTokenGenerationSucceeded(Event e) {
        var newTokens = e.getArgument(0, ArrayList.class);
        var eventCorrelationId = e.getArgument(1, CorrelationId.class);
        tokenGenerationCorrelation.get(eventCorrelationId).complete(null);
    }
    */


    public int unusedAmount(String cid){
        int count = 0;
        for (String a: unusedTokens.values())
        {
            if(a.equals(cid)){
                count++;
            }
        }

    return count;
    }
    private void handleTokenValidationRequested(Event e) {
        var token = e.getArgument(0, Token.class);
        var eventCorrelationId = e.getArgument(2, CorrelationId.class);
        if(unusedTokens.containsKey(token)){
            String cid = unusedTokens.get(token);
            unusedTokens.remove(token);
            usedTokens.put(token,cid);
            e = new Event(TOKEN_VALIDATION_COMPLETED, new Object[]{cid, eventCorrelationId});
            queue.publish(e);

        }
        else{
            e = new Event(TOKEN_VALIDATION_COMPLETED, new Object[]{"", eventCorrelationId});
            queue.publish(e);
        }
    }

}