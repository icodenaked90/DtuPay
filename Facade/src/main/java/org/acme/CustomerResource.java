package org.acme;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

//Author: Adin s164432
@Path("/customer")
public class CustomerResource {
    DTUPayService dtuPay = new DTUPayFactory().getService();

    @GET
    @Path("/report")
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<PaymentLogEntry> getPaymentList() {
        return null;
    }

    @GET
    @Path("/token")
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<String> getTokenList() {
        return "asdfghjklzxcvbnmqwer";
    }

        
    @POST
    @Path("/account")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response registerAccount(Account account) {

        //TODO: Delete the account using service
        String id = dtuPay.register(account);
        // Everything went well
        return Response.ok(id).build(); //TODO: Send correct message
    }
    /*
    @DELETE
    @Path("/account")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response registerAccount(SimpleDTUPayAccount account) {
        try {
            TODO: Delete the account using service
            String id = dtuPay.register(account.name, account.CPR, account.bankAccount);
            // Everything went well
            return Response.ok(id).build();
        } catch (NotFoundException e) {
            // We have a cid or mid error
            return Response.status(404).entity(e.getMessage()).build();
        }
    }
    */
}
