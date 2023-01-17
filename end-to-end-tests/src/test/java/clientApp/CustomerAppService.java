/*
@Author: Mila s223313
@Author: Emily s223122
@Author: Simon s163595
...
 */

package clientApp;

import clientApp.models.Account;
import clientApp.models.PaymentLogEntry;
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



    /** Requests tokens from DTUPay
     *
     * @param cid DTUPay id of the customer
     * @param numberOfTokens the number of tokens requested by the customer
     * @return tokens if success, otherwise "fail".
     */
    public String getTokens(String cid, Integer numberOfTokens) {
        var response = baseUrl.path("customer/token")
                .request()
                .post(Entity.entity(new TokenRequestCommand(cid, numberOfTokens) , MediaType.APPLICATION_JSON));
        System.out.println(response.getStatus());
        if (response.getStatus() == 200) {
            String output = response.readEntity(String.class);
            return output;
        }
        return "fail";
    }

    /** Requests all payments the customer is involved in from DTUPay
     *
     * @param amount payment amount of the customer
     * @param token the token used for payment by the customer
     * @param mid DTU Pay id of the merchant
     * @return all payments if success, otherwise "fail".
     */

    /*
    public String getReport(Integer amount, String token, String mid) {
        var response = baseUrl.path("customer/reports")
                .request()
                .post(Entity.entity(new PaymentLogEntry(amount, token, mid) , MediaType.APPLICATION_JSON));
        if (response.getStatus() == 200) {
            String output = response.readEntity(String.class);
            return output;
        }
        return "fail";
    }
    */
}

