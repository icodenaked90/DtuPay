/*
@Author: Mila s223313
...
 */

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

    /** Registers a customer with DTUPay
     *
     * @param account Account describing the customers name, cpr and bank account
     * @return DTUPay id for the customer if success, otherwise "fail".
     */
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

    /** Deregisters a customer with DTUPay
     *
     * @param id DTUPay id of the customer
     * @return "OK" if success, otherwise an error message describing the problem.
     */
    public String deregister(String id) {
        // Send registration request to DTUPay
        var response = baseUrl.path("customer/account/" + id)
                .request()
                .delete();
        // Handle response
        if (response.getStatus() == 200) {
            return "OK"; // Success
        }
        else {
            return response.readEntity(String.class); // Error message
        }
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

