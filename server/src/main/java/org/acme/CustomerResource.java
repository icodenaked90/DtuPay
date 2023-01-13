package org.acme;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;


@Path("/customer")
public class CustomerResource {
    SimpleDTUPay dtuPay = new SimpleDTUPay();

    @GET
    @Path("/report")
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<PaymentLogEntry> getPaymentList() {
        return dtuPay.getPaymentLog();
    }
/*
    @GET
    @Path("/token")
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<PaymentLogEntry> getPaymentList() {
        return dtuPay.getPaymentLog();
    }

 */
        
    @POST
    @Path("/account")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response registerAccount(SimpleDTUPayAccount account) {
        try {
            //TODO: Delete the account using service
            String id = dtuPay.register(account.name, account.CPR, account.bankAccount);
            // Everything went well
            return Response.ok(id).build();
        } catch (NotFoundException e) {
            // We have a cid or mid error
            return Response.status(404).entity(e.getMessage()).build();
        }
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
