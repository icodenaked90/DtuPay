package behaviourtests;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;

public class StudentRegistrationService {

	public Student register(Student c) {
		Client client = ClientBuilder.newClient();
		WebTarget r = client.target("http://localhost:8080/");
		var response = r.path("students").request().post(Entity.json(c), Student.class);
		return response;
	}
}
