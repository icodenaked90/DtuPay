package org.acme;

public class PaymentLogEntry {
    int amount;
    String cid;
    String mid;
    public PaymentLogEntry(int amount, String cid, String mid) {
        this.amount = amount;
        this.cid = cid;
        this.mid = mid;
    }
}
