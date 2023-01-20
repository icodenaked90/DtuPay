/*
@Author: Simon s163595
 */

package clientApp.models;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement // Needed for XML serialization and deserialization
@Data // Automatic getter and setters and equals etc
@NoArgsConstructor

public class Account {
    private String name;
    private String CPR;
    private String bankAccount;

    public Account(String _name, String _cpr, String _bankAccount){
        name = _name;
        CPR = _cpr;
        bankAccount = _bankAccount;
    }


}