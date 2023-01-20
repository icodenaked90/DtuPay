package org.acme.models;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement // Needed for XML serialization and deserialization
@Data // Automatic getter and setters and equals etc
@NoArgsConstructor // Needed for JSON deserialization and XML serialization and deserialization@AllArgsCo
public class TokenRequestCommand {
    public String cid;
    public Integer amount;

}

