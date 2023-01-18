/*
@Author: Mila s223313
@Author: Adin s164432
@Author: Hildibjørg s164539
 */

package org.acme;

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
    public Response makePayment(PaymentLogEntry payment) {

            int todo=2;//TODO:
            // Everything went well
        return Response.ok(payment).build();

    }


    @POST
    @Path("/account")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response registerAccount(Account account) {

        //TODO: Delete the account using service

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
        else {return Response.ok(response).build(); //TODO: Send correct message
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
