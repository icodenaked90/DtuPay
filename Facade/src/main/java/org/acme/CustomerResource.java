/*
@Author: Mila s223313
...
 */

package org.acme;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

import static org.eclipse.persistence.config.ResultType.Array;

//Author: Adin s164432
@Path("/customer")
public class CustomerResource {
    DTUPayService dtuPay = new DTUPayFactory().getService();

    @GET
    @Path("/report")
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<PaymentLogEntry> getPaymentList() {
        return null;
    }

    @POST
    @Path("/token")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTokenList(TokenRequestCommand tokenRequestCommand) {
        ArrayList<String> tokenList =new ArrayList<String>();
        tokenList.add("hsauigjahgfjghsiufh");
        System.out.println(tokenRequestCommand.cid);
        return Response.ok(tokenList).build(); //TODO: Send correct message
    }

        
    @POST
    @Path("/account")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response registerAccount(Account account) {
        //TODO: Delete the account using service
        String id = dtuPay.register(account); //= dtuPay.register(account);
        // Everything went well
        return Response.ok(id).build(); //TODO: Send correct message
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

    /*
    @DELETE
    @Path("/account")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response registerAccount(SimpleDTUPayAccount account) {
        try {
            TODO: Delete the account using service
            String id = dtuPay.register(account.name, account.CPR, account.bankAccount);
            // Everything went well
            return Response.ok(id).build();
        } catch (NotFoundException e) {
            // We have a cid or mid error
            return Response.status(404).entity(e.getMessage()).build();
        }
    }
    */
}
