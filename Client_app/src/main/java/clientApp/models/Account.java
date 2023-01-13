package clientApp.models;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement // Needed for XML serialization and deserialization
@Data // Automatic getter and setters and equals etc
@NoArgsConstructor

public class Account {
    private String name;
    private String cpr;
    private String bankAccount;

    public Account(String _name, String _cpr, String _bankAccount){
        name = _name;
        cpr = _cpr;
        bankAccount = _bankAccount;
    }


}