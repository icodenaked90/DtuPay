/*
@Author: Mila s223313
@Author: Emily s223122
*/

package clientApp;

import clientApp.models.Account;
import clientApp.models.ReportRequestCommand;
import clientApp.models.ResponseStatus;
import clientApp.models.TokenRequestCommand;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;

import java.lang.reflect.Array;
import java.util.ArrayList;


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
            return "ok"; // Success
        }
        else {
            return "fail"; // Error message
        }
    }

    public String getToken(String cid, Integer amount) {
        var response = baseUrl.path("customer/token")
                .request()
                .post(Entity.entity(new TokenRequestCommand(cid, amount) , MediaType.APPLICATION_JSON));

        if (response.getStatus() == 200) {
            return response.readEntity(String.class);

        }

        return "fail";
    }

    public String getReport(String cid) {
        var response = baseUrl.path("customer/report")
                .request()
                .post(Entity.entity(new ReportRequestCommand(cid) , MediaType.APPLICATION_JSON));
        if (response.getStatus() == 200) {
            return response.readEntity(String.class);
        }
        return "fail";
    }
}

