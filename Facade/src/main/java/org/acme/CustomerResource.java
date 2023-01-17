/*
@Author: Mila s223313
@Author: Adin s164432
...
 */

package org.acme;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

import static org.eclipse.persistence.config.ResultType.Array;

@Path("/customer")
public class CustomerResource {
    DTUPayService dtuPay = new DTUPayFactory().getService();

    @GET
    @Path("/report")
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<PaymentLogEntry> getPaymentList() {
        return null;
    }

    @POST
    @Path("/token")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTokenList(TokenRequestCommand tokenRequestCommand) {
        TokenList list = dtuPay.generateTokens(tokenRequestCommand);
        return Response.ok(list).build();
    }

        
    @POST
    @Path("/account")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response registerAccount(Account account) {
        //TODO: Delete the account using service
        String id = dtuPay.register(account); //= dtuPay.register(account);
        // Everything went well
        return Response.ok(id).build(); //TODO: Send correct message
    }

    @DELETE
    @Path("/account/{id}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deregisterAccount(@PathParam("id")String id) {
            String errorMessage = dtuPay.deregister(id);
            if (errorMessage.equals(""))
                return Response.ok(id).build();
            else
                return Response.status(404).entity(errorMessage).build();
    }
}
