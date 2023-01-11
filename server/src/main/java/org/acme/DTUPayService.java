package org.acme;

public class DTUPayService {
    private MessageQueue queue;

    public DTUPayService(MessageQueue q) {
        queue = q;
        queue.addHandler("StudentIdAssigned", this::handleStudentIdAssigned);
    }

    public Student register(Student s) {
        registeredStudent = new CompletableFuture<>();
        Event event = new Event("StudentRegistrationRequested", new Object[] { s });
        queue.publish(event);
        return registeredStudent.join();
    }

    public void handleStudentIdAssigned(Event e) {
        var s = e.getArgument(0, Student.class);
        registeredStudent.complete(s);
    }
}
