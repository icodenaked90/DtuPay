/*
@Author: Emily s223122
@Author: Simon s163595
...
 */

package clientApp;

import clientApp.models.Account;
import clientApp.models.PaymentLogEntry;
import clientApp.models.ResponseStatus;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

public class MerchantAppService {
    WebTarget baseUrl;

    public MerchantAppService() {
        Client client = ClientBuilder.newClient();
        // baseUrl = client.target("http://host.docker.internal:8080/");
        baseUrl = client.target("http://localhost:8080/");
    }

    public PaymentLogEntry[] getLog(String mediaType) {

        return baseUrl.path("merchant/report")
                .request()
                .accept(mediaType)
                .get(PaymentLogEntry[].class);
    }

    public ResponseStatus setNewPayment(PaymentLogEntry payment, String mediaType) {
        var response = baseUrl.path("merchant/payment")
                .request()
                .post(Entity.entity(payment, mediaType));

        if (response.getStatus() == 200) {
            return new ResponseStatus(true, response.readEntity(String.class));
        }

        return new ResponseStatus(false, response.readEntity(String.class));
    }

    public String registerNewMerchantAccount(Account account) {

        var response = baseUrl.path("merchant/account")
                .request()
                .post(Entity.entity(account, MediaType.APPLICATION_JSON));
        System.out.println(response.getStatus());
        if (response.getStatus() == 200) {
            String output = response.readEntity(String.class);
            return output;
        }

        return "fail";
    }
}