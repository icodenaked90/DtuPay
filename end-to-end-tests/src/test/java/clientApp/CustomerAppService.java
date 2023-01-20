/*
@Author: Mila s223313
@Author: Emily s223122
@Author: Simon s163595
...
 */

package clientApp;

import clientApp.models.*;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;

import java.util.List;


public class CustomerAppService {
    WebTarget baseUrl;

    public CustomerAppService() {
        Client client = ClientBuilder.newClient();
        baseUrl = client.target("http://localhost:8080/");
    }

    /**
     * Registers a customer with DTUPay
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

    /**
     * Deregisters a customer with DTUPay
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
        } else {
            return response.readEntity(String.class); // Error message
        }
    }

    /**
     * Requests tokens from DTUPay
     *
     * @param cid            DTUPay id of the customer
     * @param numberOfTokens the number of tokens requested by the customer
     * @return tokens if success
     * @throws Exception if failed
     */
    public TokenList getTokens(String cid, Integer numberOfTokens) throws Exception {
        var response = baseUrl.path("customer/token")
                .request()
                .post(Entity.entity(new TokenRequestCommand(cid, numberOfTokens), MediaType.APPLICATION_JSON));

        if (response.getStatus() == 200)
            return response.readEntity(TokenList.class);
        else
            throw new Exception(response.readEntity(String.class));
    }

    /**
     * Requests all payments the customer is involved in from DTUPay
     *
     * @param cid Id of the customer
     * @return all payments if success, otherwise "fail".
     */
    public CustomerReport getReport(String cid) {
        var response = baseUrl.path("customer/report")
                .request()
                .post(Entity.entity(cid, MediaType.APPLICATION_JSON));
        return response.readEntity(CustomerReport.class);
    }
}

