package Payment.models;

import lombok.Data;

@Data
public class Token {
    String id;

    public Token(String id) {
        this.id = id;
    }
}
