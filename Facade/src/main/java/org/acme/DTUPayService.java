package org.acme;


import AccountManagement.Account;
import messaging.Event;
import messaging.MessageQueue;

import java.util.concurrent.CompletableFuture;

//Author: Adin s164432
public class DTUPayService {
    private MessageQueue queue;

    public DTUPayService(MessageQueue q) {
        queue = q;
        queue.addHandler("registrationIDAssigned", this::handleStudentIdAssigned);

    }

    public Account register(Account a) {
        registeredStudent = new CompletableFuture<>();
        Event event = new Event("StudentRegistrationRequested", new Object[] { a });
        queue.publish(event);
        return registeredStudent.join();
    }

    public void handleStudentIdAssigned(Event e) {
        var s = e.getArgument(0, Student.class);
        registeredStudent.complete(s);
    }
}
