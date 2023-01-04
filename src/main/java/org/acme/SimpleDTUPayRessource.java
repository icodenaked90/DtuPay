package org.acme;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;

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
        dtuPay.pay(payment.amount, payment.cid, payment.mid);
        return payment;
    }
}
