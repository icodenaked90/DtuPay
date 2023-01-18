/*
@Author: Mila s223313
@Author: Adin s164432
@Author: Jonathan s194134
...
 */

package org.acme;

import org.acme.models.NewPayment;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

@Path("/merchant")
public class MerchantResource {
    DTUPayService dtuPay = new DTUPayFactory().getService();

    @GET
    @Path("/report")
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<PaymentLogEntry> getPaymentList() {
        return null;
    }

    @POST
    @Path("/payment")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response makePayment(NewPayment payment) {

        NewPayment completePayment =  dtuPay.pay(payment);

        // Success scenario
        if (completePayment.isPaymentSuccesful()) return Response.ok(payment).build();
        // Failure scenario
        return Response.status(404).entity(completePayment.getErrorMessage()).build();
    }


    @POST
    @Path("/account")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response registerAccount(Account account) {

        //TODO: Delete the account using service
        String mid = dtuPay.register(account);
        // Everything went well
        return Response.ok(mid).build(); //TODO: write correct response
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
