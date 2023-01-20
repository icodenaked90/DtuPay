package org.acme.models;

import java.util.ArrayList;
//@Author: Jonathan s194134
public class TransactionLog {
    String requesterId;
    ArrayList<Transaction> log = new ArrayList<>();

    public void addToLog(Transaction transaction) {
        log.add(transaction);
    }
    public String getId() {
        return  requesterId;
    }
    public void setId(String id) {
        this.requesterId = id;
    }
}