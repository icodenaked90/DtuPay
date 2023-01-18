package org.acme;


import org.acme.models.ManagerReport;
import org.acme.models.ManagerReportEntry;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

//Author: Adin s164432

public class ReportResource {
    DTUPayService dtuPay = new DTUPayFactory().getService();
    @POST
    @Path("/report")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getReport(String maid) {
        ManagerReport managerReport = new ManagerReport();
        managerReport.addToLog(new ManagerReportEntry(2,"a"));
        //ManagerReportRequestResponse response = new ManagerReportRequestResponse(managerReport);
                //dtuPay.getManagerReport(maid);

        //if (response.isError()) {
        return Response.status(200).entity(managerReport).build();
        //} else {
          //  return Response.ok(response.getReport()).build();
        //}
    }
}
