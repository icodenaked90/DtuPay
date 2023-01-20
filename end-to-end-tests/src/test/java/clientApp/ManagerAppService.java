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
        baseUrl = client.target("http://localhost:8080/");
    }

    public ManagerReport getReports(String maid) {
        var response = baseUrl.path("report")
                .request()
                .post(Entity.entity(maid, MediaType.APPLICATION_JSON));

        return response.readEntity(ManagerReport.class);
    }
}