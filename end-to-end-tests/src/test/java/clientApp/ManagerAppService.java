/*
@Author: Mila s223313
@Author: Emily s223122
*/

package clientApp;

import clientApp.models.Account;
import clientApp.models.ReportRequestCommand;
import clientApp.models.TokenRequestCommand;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;


public class ManagerAppService {
    WebTarget baseUrl;

    public ManagerAppService() {
        Client client = ClientBuilder.newClient();
        // baseUrl = client.target("http://host.docker.internal:8080/");
        baseUrl = client.target("http://localhost:8080/");
    }

    public String getReport(String manid) {
        var response = baseUrl.path("customer/report")
                .request()
                .post(Entity.entity(new ReportRequestCommand(manid) , MediaType.APPLICATION_JSON));
        if (response.getStatus() == 200) {
            return response.readEntity(String.class);
        }
        return "fail";
    }
}

