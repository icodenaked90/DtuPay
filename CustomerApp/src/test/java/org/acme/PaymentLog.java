package org.acme;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.xml.bind.annotation.XmlRootElement;


import java.util.ArrayList;

@XmlRootElement // Needed for XML serialization and deserialization
@Data // Automatic getter and setters and equals etc
@NoArgsConstructor // Needed for JSON deserialization and XML serialization and deserialization@AllArgsConstructor
@AllArgsConstructor
public class PaymentLog {
    public ArrayList<PaymentLogEntry> log;

}
