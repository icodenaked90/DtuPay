package org.acme;


import org.acme.models.ManagerReport;
import org.acme.models.ManagerReportEntry;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

//Author: Emily (s16223122)
@Path("/report")
public class ReportResource {
    DTUPayService dtuPay = new DTUPayFactory().getService();
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getReport(String maid) {
        ManagerReportRequestResponse a = dtuPay.getManagerReport(maid);
        return Response.status(200).entity(a.getReport()).build();
    }
}
