package clientApp;

import clientApp.models.ResponseStatus;
import clientApp.models.TokenRequestCommand;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;


public class CustomerAppService {
    WebTarget baseUrl;

    public CustomerAppService() {
        Client client = ClientBuilder.newClient();
        // baseUrl = client.target("http://host.docker.internal:8080/");
        baseUrl = client.target("http://localhost:8080/");
    }

    public ResponseStatus pay(int amount, String cid, String mid) {
        //Accept the payment and send the token to the merchatn
        return new ResponseStatus(true, "hej");
    }

    public String getToken(String cid, Integer amount) {
        var response = baseUrl.path("customer/token")
                .request()
                .post(Entity.entity(new TokenRequestCommand(cid, amount) , MediaType.APPLICATION_JSON));
        System.out.println(response.getStatus());
        if (response.getStatus() == 200) {
            String output = response.readEntity(String.class);
            return output;
        }

        return "fail";
    }
}

