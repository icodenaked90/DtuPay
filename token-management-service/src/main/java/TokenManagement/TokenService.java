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

public class TokenService {
    // Map from Token to AccountId
    private Map<Token, String> tokens = new ConcurrentHashMap<>();  // SQL database table mapping Token to Account ID
    // Map from account id to unused tokens
    private Map<String, ArrayList<Token>> unusedTokens = new ConcurrentHashMap<>(); // Equivalent to database SQL query on tok
    // Map from account id to used tokens (for reporting purposes)
    private Map<String, ArrayList<Token>> usedTokens = new ConcurrentHashMap<>();

    private MessageQueue queue;
    public TokenService(MessageQueue q) {
        queue = q;
        queue.addHandler("TokenGenerationRequested", this::handleTokenGenerationRequested);
        queue.addHandler("TokenGenerationSucceeded", this::handleTokenGenerationSucceeded);
        queue.addHandler("TokenGenerationFailed", this::handleTokenGenerationFailed);
        queue.addHandler("CustomerValidationCompleted", this::handleCustomerValidationCompleted);
        //queue.addHandler("TokenValidationRequested", this::handleTokenValidationRequested);
    }

    private Map<CorrelationId, CompletableFuture<Boolean>> customerValidationRequested = new ConcurrentHashMap<>();

    public void handleCustomerValidationCompleted(Event e) {
        var customerOk = e.getArgument(0, Boolean.class);
        var eventCorrelationId = e.getArgument(1, CorrelationId.class);
        customerValidationRequested.get(eventCorrelationId).complete(customerOk);
    }

    public void handleTokenGenerationRequested(Event e) {
        var accountId = e.getArgument(0, String.class);
        var numberOfTokens = e.getArgument(1, int.class);
        var eventCorrelationId = e.getArgument(2, CorrelationId.class);

        // TODO Validate accountId against account management service
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
            e = new Event("TokenGenerationFailed", new Object[]{"Invalid number of tokens requested", eventCorrelationId});
            queue.publish(e);
            return;
        }
        // If account has never used token service before, create empty list of unused tokens
        if (!unusedTokens.containsKey(accountId))
            unusedTokens.put(accountId, new ArrayList<Token>());
        // Validate generation request
        var accountUnusedTokens = unusedTokens.get(accountId);
        if (accountUnusedTokens.size() > 1) {
            e = new Event("TokenGenerationFailed", new Object[]{"Account already owns more than one unused token", eventCorrelationId});
            queue.publish(e);
            return;
        }
        // Create tokens for account
        ArrayList<Token> accountNewTokens = new ArrayList<Token>();
        for (int i = 0; i < numberOfTokens; i++) {
            Token token = Token.generateToken();
            if (tokens.containsKey(token))
                i--;
            else {
                tokens.put(token, accountId);
                accountUnusedTokens.add(token);
                accountNewTokens.add(token);
            }
        }
        // Return new tokens
        e = new Event("TokenGenerationSucceeded", new Object[]{accountNewTokens, eventCorrelationId});
        queue.publish(e);
    }



    /// TESTING ///

    private Map<CorrelationId, CompletableFuture<ArrayList<Token>>> tokenGenerationCorrelation = new ConcurrentHashMap<>();

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

    public ArrayList<Token> generateTokens(String accountId, int numberOfTokens) {
        var correlationId = CorrelationId.randomId();
        tokenGenerationCorrelation.put(correlationId, new CompletableFuture<>());
        Event event = new Event("TokenGenerationRequested", new Object[]{accountId, numberOfTokens, correlationId});
        queue.publish(event);
        return tokenGenerationCorrelation.get(correlationId).join();
    }

}