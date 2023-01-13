package clientApp.models;

import lombok.Data;

@Data
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