// Token class for generating tokens and validating them
// Built when mob programming
// @Author: Jonathan Zørn (S194134)
// @Author: Adin (s164432)
// @Author: Mila (s223313)
// @Author: Simon Philipsen (S163595)
package org.acme;

import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@XmlRootElement // Needed for XML serialization and deserialization
@Data // Automatic getter and setters and equals etc
@NoArgsConstructor // Needed for JSON deserialization and XML serialization and deserialization@AllArgsCo
public class Token {
    String id;
}