package org.acme;


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;

@Path("/report")
public class ReportResource {

    SimpleDTUPay dtuPay = new SimpleDTUPay();//TODO:

    @GET
    @Path("/report")
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<PaymentLogEntry> getPaymentList() {
        return dtuPay.getPaymentLog();
    }

}
