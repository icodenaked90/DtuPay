package Payment.models;

import java.util.ArrayList;
// @Author: Jonathan (s194134)
public class TransactionLog {
    String requesterId;
    ArrayList<Transaction> log = new ArrayList<>();
    // @Author: Jonathan (s194134)
    public void addToLog(Transaction transaction) {
        log.add(transaction);
    }
    // @Author: Jonathan (s194134)
    public String getId() {
        return  requesterId;
    }
    // @Author: Jonathan (s194134)
    public void setId(String id) {
        this.requesterId = id;
    }
}
