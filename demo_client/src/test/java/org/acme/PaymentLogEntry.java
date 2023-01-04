package org.acme;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement // Needed for XML serialization and deserialization
@Data // Automatic getter and setters and equals etc
@NoArgsConstructor // Needed for JSON deserialization and XML serialization and deserialization@AllArgsConstructor
@AllArgsConstructor
public class PaymentLogEntry {
    int amount;
    String cid;
    String mid;
    public String toString(){
        return "amount = "+amount+" cid = "+cid+" mid = "+mid;
    }
}
