package org.acme;

import javax.xml.bind.annotation.XmlRootElement;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@XmlRootElement // Needed for XML serialization and deserialization
@Data // Automatic getter and setters and equals etc
@NoArgsConstructor // Needed for JSON deserialization and XML serialization and deserialization
@AllArgsConstructor
public class SimpleDTUPay {
    private String cid = "cid1";
    private String mid = "mid1";

    private ArrayList<PaymentLogEntry> paymentLog = new ArrayList<PaymentLogEntry>();

    public boolean pay(int amount, String cid, String mid) {
        if (cid == this.cid && mid == this.mid) {
            paymentLog.add(new PaymentLogEntry(amount, cid, mid));
            return true;
        }
        return false;
    }

    public void testFunction() {
        paymentLog.add(new PaymentLogEntry(2, "cid1", "mid1"));
    }
}
