package org.acme;

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
    public PaymentLogEntry makePayment(PaymentLogEntry payment) {
        try {
            dtuPay.pay(payment.amount, payment.cid, payment.mid);
            return payment;
        } catch (NotFoundException e) {
            throw new WebApplicationException("akjhsdasd", 404);
        }
    }
}
