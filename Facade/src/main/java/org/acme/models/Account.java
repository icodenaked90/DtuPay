package org.acme.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlRootElement;

//@Author: Jonathan s194134
@XmlRootElement // Needed for XML serialization and deserialization
@Data // Automatic getter and setters and equals etc
@NoArgsConstructor
public class Account {
    String name;
    String CPR;
    String bankAccount;
}