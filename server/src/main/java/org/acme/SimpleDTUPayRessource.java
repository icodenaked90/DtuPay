package org.acme;

import dtu.ws.fastmoney.BankServiceException_Exception;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import javax.ws.rs.core.Response;

@Path("/dtupay")
public class SimpleDTUPayRessource {
    SimpleDTUPay dtuPay = new SimpleDTUPay();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<PaymentLogEntry> getPaymentList() {
        return dtuPay.getPaymentLog();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response makePayment(PaymentLogEntry payment) {
        try {
            dtuPay.pay(payment.amount, payment.cid, payment.mid);
            // Everything went well
            return Response.ok(payment).build();
        } catch (NotFoundException e) {
            // We have a cid or mid error
            return Response.status(404).entity(e.getMessage()).build();
        }

    }
}
