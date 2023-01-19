/*
@Author: Emily s223122
@Author: Simon s163595
@Author: Mila s223313
...
 */

package clientApp;

import clientApp.models.ManagerReport;
import clientApp.models.MerchantReport;
import clientApp.models.ResponseStatus;

import clientApp.models.TokenList;
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

    public ManagerReport getReports(String maid) {
        var response = baseUrl.path("report")
                .request()
                .post(Entity.entity(maid, MediaType.APPLICATION_JSON));
        System.out.println("Hej");
        System.out.println(response.readEntity(ManagerReport.class).getLog().get(0).toString());
        return new ManagerReport();
    }
}