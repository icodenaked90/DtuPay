/*
@Author: Mila s223313
@Author: Adin s164432
@Author: Jonathan s194134
...
 */

package org.acme;

import org.acme.models.Account;
import org.acme.models.NewPayment;
import org.acme.models.PaymentLogEntry;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;


@Path("/merchant")
public class MerchantResource {
    DTUPayService dtuPay = new DTUPayFactory().getService();


    //@Author: Emily s223122
    @POST
    @Path("/report")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getReport(String mid) {
        MerchantReportRequestResponse a = dtuPay.getMerchantReport(mid);
        return Response.status(200).entity(a.getReport()).build();
    }

    //@Author: Jonathan s194134
    @POST
    @Path("/payment")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response makePayment(NewPayment payment) {

        NewPayment completePayment =  dtuPay.pay(payment);

        // Success scenario
        if (completePayment.isPaymentSuccesful()) return Response.ok(completePayment).build();
        // Failure scenario
        return Response.status(400).entity(completePayment.getErrorMessage()).build();
    }


    //@Author: Adin s164432
    @POST
    @Path("/account")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response registerAccount(Account account) {

        String response = dtuPay.register(account);
        // Everything went well
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

    // @Author: Mila s223313
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
