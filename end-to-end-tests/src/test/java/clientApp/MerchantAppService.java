/*
@Author: Emily s223122
@Author: Simon s163595
@Author: Mila s223313
...
 */

package clientApp;

import clientApp.models.Account;
import clientApp.models.NewPayment;
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

    /** Registers a merchant with DTUPay
     *
     * @param account Account describing the merchant's name, cpr and bank account
     * @return DTUPay id for the merchant if success, otherwise "fail".
     */
    public String register(Account account) {
        // Send registration request to DTUPay
        var response = baseUrl.path("merchant/account")
                .request()
                .post(Entity.entity(account, MediaType.APPLICATION_JSON));
        // Handle request
        if (response.getStatus() == 200) {
            String mid = response.readEntity(String.class);
            return mid;
        }
        // Failure handling
        //throw new Exception(response.readEntity(String.class));
        return "fail";
    }

    /** Deregisters a merchant with DTUPay
     *
     * @param id DTUPay id of the merchant
     * @return "OK" if success, otherwise an error message describing the problem.
     */
    public String deregister(String id) {
        // Send deregistration request to DTUPay
        var response = baseUrl.path("merchant/account/" + id)
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


    public ResponseStatus pay(String token, String merchantId, int amount) {
        //Create payment object to package the payment info
        NewPayment completedPayment;
        NewPayment payment = new NewPayment(token, merchantId, amount);

        // Send request
        var response = baseUrl.path("merchant/payment")
                .request()
                .post(Entity.entity(payment, MediaType.APPLICATION_JSON));

        // Handle request
        if (response.getStatus() == 200) {
            completedPayment = response.readEntity(NewPayment.class);
            if (completedPayment.isPaymentSuccesful()) {
                // valid payment
                return new ResponseStatus(true, "");
            }
            // Payment failed with a known common error
            return new ResponseStatus(false, completedPayment.getErrorMessage());
        }
        return new ResponseStatus(false, "unknown error");
    }


    /*
    public PaymentLogEntry[] getLog(String mediaType) {

        return baseUrl.path("merchant/report")
                .request()
                .accept(mediaType)
                .get(PaymentLogEntry[].class);
    }
      */


    public ResponseStatus getReports(String mid) {
        var response = baseUrl.path("merchant/reports")
                .request()
                .post(Entity.entity(mid, MediaType.TEXT_PLAIN_TYPE));

        if (response.getStatus() == 200) {
            return new ResponseStatus(true, response.readEntity(String.class));
        }

        return new ResponseStatus(false, response.readEntity(String.class));
    }


}