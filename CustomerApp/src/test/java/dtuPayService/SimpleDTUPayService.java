package dtuPayService;

import dtu.ws.fastmoney.AccountInfo;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import org.acme.PaymentLogEntry;
import org.acme.ResponseStatus;
import org.acme.SimpleDTUPayAccount;

import java.util.ArrayList;


public class SimpleDTUPayService {

    WebTarget baseUrl;

    public SimpleDTUPayService() {
        Client client = ClientBuilder.newClient();
        // baseUrl = client.target("http://host.docker.internal:8080/");
        baseUrl = client.target("http://localhost:8080/");
    }

    public PaymentLogEntry[] getPaymentLog() {
        return getLog(MediaType.APPLICATION_JSON);
    }


    public ResponseStatus pay(int amount, String cid, String mid) {
        PaymentLogEntry entry = new PaymentLogEntry(amount, cid, mid);
        return setNewPayment(entry, MediaType.APPLICATION_JSON);
    }

    public String register(String name, String cpr, String bankAccount) {
        SimpleDTUPayAccount account = new SimpleDTUPayAccount(name, cpr, bankAccount);
        return registerNewAccount(account);
    }

    private PaymentLogEntry[] getLog(String mediaType) {

        return baseUrl.path("customer/report")
                .request()
                .accept(mediaType)
                .get(PaymentLogEntry[].class);
    }

    private String registerNewAccount(SimpleDTUPayAccount account) {

        var response = baseUrl.path("customer/account")
                .request()
                .post(Entity.entity(account, MediaType.APPLICATION_JSON));

        if (response.getStatus() == 200) {
            String output = response.readEntity(String.class);
            return output;
        }

        return "";
    }
}