package org.acme;

public class Main {
    public static void main(String[] args) throws Exception {
        new StartUp().startUp();
    }

    private void startUp() throws Exception {
        var mq = new RabbitMqQueue("rabbitMq");
        new AccountIdService(mq);
    }
}