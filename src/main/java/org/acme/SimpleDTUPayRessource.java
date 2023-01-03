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
        System.out.println("This is the list:"+dtuPay.getPaymentLog().toString());;
        return dtuPay.getPaymentLog();
    }
    @GET
    @Path("nice")
    @Produces(MediaType.TEXT_PLAIN)
    public String nice() {
        dtuPay.testFunction();
        return "Nice";
    }
    //@POST
    //@Consumes(MediaType.APPLICATION_JSON)
    //public PaymentLogEntry makePayment() {
    //    return dtuPay.getPaymentLog();
    //}
}
