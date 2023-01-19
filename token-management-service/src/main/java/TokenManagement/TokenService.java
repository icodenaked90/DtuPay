/*
@Author: Mila (s223313)
@Author: Adin (s164432)
 */

package TokenManagement;

import java.util.Map;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

import TokenManagement.models.CorrelationId;
import TokenManagement.models.Token;
import TokenManagement.models.TokenList;
import TokenManagement.models.TokenRequestResponse;
import messaging.Event;
import messaging.MessageQueue;

public class TokenService implements ITokenService{
    private Map<Token, String> unusedTokens = new ConcurrentHashMap<>();
    private Map<Token, String> usedTokens = new ConcurrentHashMap<>();
    private MessageQueue queue;

    public TokenService(MessageQueue q) {
        queue = q;

        queue.addHandler(TOKEN_GENERATION_REQUESTED, this::handleTokenGenerationRequested);
        queue.addHandler(TOKEN_VALIDATION_REQUESTED, this::handleTokenValidationRequested);

    }


    public void handleTokenGenerationRequested(Event e) {
        var command = e.getArgument(0, TokenRequestCommand.class);
        var eventCorrelationId = e.getArgument(1, CorrelationId.class);
        String accountId = command.cid;
        int numberOfTokens = command.amount;
        // Send event CustomerValidationRequested to AccountManagementService with accountId and correlationid
        // Account management service responds with CustomerValidationCompleted event with bool whether account is ok (exists and is customer) and the correlationId

        // Validate token number
        if (numberOfTokens < 1 || numberOfTokens > 5) {
            e = new Event(TOKEN_GENERATION_COMPLETED, new Object[]{new TokenRequestResponse("Invalid number of tokens requested."), eventCorrelationId});
            queue.publish(e);
            return;
        }

        // Validate generation request
        if (unusedAmount(accountId) > 1) {
            e = new Event(TOKEN_GENERATION_COMPLETED, new Object[]{new TokenRequestResponse("User already owns more than one token."), eventCorrelationId});
            queue.publish(e);
            return;
        }

        // Create tokens for account
        ArrayList<Token> accountNewTokens = new ArrayList<>();
        for (int i = 0; i < numberOfTokens; i++) {
            while(true) {
                Token token = Token.generateToken();
                if (!unusedTokens.containsValue(token) && !usedTokens.containsValue(token)) {
                    unusedTokens.put(token, accountId);
                    accountNewTokens.add(token);
                    break;
                }
            }
        }
        // Return new tokens
        e = new Event(TOKEN_GENERATION_COMPLETED, new Object[]{new TokenRequestResponse(new TokenList(accountNewTokens)), eventCorrelationId});
        queue.publish(e);
    }


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

    public void handleTokenValidationRequested(Event e) {
        var token = e.getArgument(0, Token.class);
        var eventCorrelationId = e.getArgument(1, CorrelationId.class);
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
    //Used for tests
    public Map<Token, String> getUnused(){
        return unusedTokens;
    }

    //TODO should be deleted?
    public void removeAccountFromTokenList(String id){
        for (Token token : unusedTokens.keySet()) {
            if(unusedTokens.get(token).equals(id)){
                unusedTokens.remove(token);
            }
        }
    }
    public ArrayList<Token> addTokensToAccount(String id, int amount){
        ArrayList<Token> temp = new ArrayList<>();
        for(int i = 0; i<amount;i++){
            Token token = Token.generateToken();
            unusedTokens.put(token,id);
            temp.add(token);
        }
        return temp;
    }
}