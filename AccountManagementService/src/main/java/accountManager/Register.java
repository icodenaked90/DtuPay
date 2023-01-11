package accountManager;

import java.util.HashMap;
import java.util.UUID;

public class Register {

    private HashMap<String, String> userAccounts = new HashMap<String, String>();

    public String register(String name, String CPR, String bankAccount) {
        String id = generateId();
        userAccounts.put(id,bankAccount);
        return id;
    }

    private String generateId(){
        while(true) {
            String uuid = UUID.randomUUID().toString();
            if (!userAccounts.containsKey(uuid)) return uuid;
        }
    }
}
