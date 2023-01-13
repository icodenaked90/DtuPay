package clientApp;

import clientApp.models.ResponseStatus;
import clientApp.models.TokenRequestCommand;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;

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

    public String getToken(TokenRequestCommand tokencommand) {
        var response = baseUrl.path("Token")
                .request()
                .post(Entity.entity(tokencommand, MediaType.APPLICATION_JSON));

        if (response.getStatus() == 200) {
            String output = response.readEntity(String.class);
            return output;
        }

        return "fail";
    }
}

