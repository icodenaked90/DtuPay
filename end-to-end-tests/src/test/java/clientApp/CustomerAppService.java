package clientApp;

import clientApp.models.Account;
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

    public String register(Account account) {

        // Send registration request to DTUPay
        var response = baseUrl.path("customer/account")
                .request()
                .post(Entity.entity(account, MediaType.APPLICATION_JSON));
        // Handle response
        if (response.getStatus() == 200) {
            // Success, payload = new customer id
            String cid = response.readEntity(String.class);
            return cid;
        }

        // Failure handling
        //throw new Exception(response.readEntity(String.class));
        return "fail";
    }
    //////////


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

