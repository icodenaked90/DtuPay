package org.acme.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlRootElement;
//@Author: Jonathan s194134
@XmlRootElement // Needed for XML serialization and deserialization
@Data // Automatic getter and setters and equals etc
@NoArgsConstructor // Needed for JSON deserialization and XML serialization and deserialization@AllArgsConstructor
public class PaymentLogEntry {
    int amount;
    String token;
    String mid;
    public PaymentLogEntry(int amount, String token, String mid) {
        this.amount = amount;
        this.token = token;
        this.mid = mid;
    }
}
