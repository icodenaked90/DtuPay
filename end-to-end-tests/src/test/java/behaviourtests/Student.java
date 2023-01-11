package behaviourtests;

import java.io.Serializable;

import lombok.Data;

@Data
public class Student implements Serializable {

	private static final long serialVersionUID = 9023222981284806610L;
	private String name;
	private String id;
}
