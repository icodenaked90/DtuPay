/*
@Author: Mila s223313
@Author: Adin s164432
@Author: Hildibjørg s164539
 */

package org.acme;

import org.acme.models.Account;
import org.acme.models.PaymentLogEntry;
import org.acme.models.TokenRequestCommand;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;


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
        TokenRequestResponse response = dtuPay.generateTokens(tokenRequestCommand);

        if (response.isError()) {
            return Response.status(400).entity(response.getErrorMessage()).build();
        } else {
            return Response.ok(response.getTokens()).build();
        }
    }
        
    @POST
    @Path("/account")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response registerAccount(Account account) {

        //the response can either be an error message or
        //an id for the registered account
        String response = dtuPay.register(account);

        //check name
        if (response.equals("Name has a wrong format")) {
            return Response.status(400).entity(response).build();
        }
        //check cpr
        else if (response.equals("CPR number has a wrong format")){
            return Response.status(400).entity(response).build();
        }
        //everything went well
        else {return Response.ok(response).build();
        }
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
