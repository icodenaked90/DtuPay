package accountManager;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

import messaging.Event;
import messaging.MessageQueue;

public class StudentRegistrationService {

    public static final String STUDENT_REGISTRATION_REQUESTED = "StudentRegistrationRequested";
    public static final String STUDENT_ID_ASSIGNED = "StudentIdAssigned";
    private MessageQueue queue;
    private Map<CorrelationId, CompletableFuture<Student>> correlations = new ConcurrentHashMap<>();

    public StudentRegistrationService(MessageQueue q) {
        queue = q;
        queue.addHandler(STUDENT_ID_ASSIGNED, this::handleStudentIdAssigned);
    }

    public Student register(Student s) {
        var correlationId = CorrelationId.randomId();
        correlations.put(correlationId,new CompletableFuture<>());
        Event event = new Event(STUDENT_REGISTRATION_REQUESTED, new Object[] { s, correlationId });
        queue.publish(event);
        return correlations.get(correlationId).join();
    }

    public void handleRegistrantion(Event e) {
        var s = e.getArgument(0, Student.class);
        var correlationid = e.getArgument(1, CorrelationId.class);
        correlations.get(correlationid).complete(s);
    }
}

/* DEL AF RESSOURCE I DTUPAY
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/account")
public class AccountManagementResource {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registerUser(DTUPayAccount account) {
        try {
            Register userRegister = new Register();
            String id = userRegister.register(account.name, account.CPR, account.bankAccount);
            // Everything went well
            return Response.ok(id).build();
        } catch (NotFoundException e) {
            // We have a cid or mid error
            return Response.status(404).entity(e.getMessage()).build();
        }
    }
} */