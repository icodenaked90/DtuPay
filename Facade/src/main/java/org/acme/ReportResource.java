package org.acme;


import org.acme.models.PaymentLogEntry;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
//Author: Adin s164432
@Path("/report")
public class ReportResource {
    DTUPayService dtuPay = new DTUPayFactory().getService();
    
    @GET
    @Path("/report")
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<PaymentLogEntry> getPaymentList() {
        return null;
    }

}
