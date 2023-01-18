package Payment.models;

import java.util.ArrayList;

public class TransactionLog {
    ArrayList<Transaction> log = new ArrayList<>();

    public void addToLog(Transaction transaction) {
        log.add(transaction);
    }
}
