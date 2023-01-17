package TokenManagement;

import java.util.ArrayList;

// @Author: Adin (s164432)
public class TokenList {
    private ArrayList<Token> tokens;
    public TokenList(ArrayList<Token> tokens){
        this.tokens = tokens;
    }
    public ArrayList<Token> getTokens() {
        return tokens;
    }

    public void setTokens(ArrayList<Token> tokens) {
        this.tokens = tokens;
    }
}
