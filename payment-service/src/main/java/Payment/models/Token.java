package Payment.models;

import lombok.Data;

//@Author: Jonathan s194134
@Data
public class Token {
    String id;

    public Token(String id) {
        this.id = id;
    }
}
