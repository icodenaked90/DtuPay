package AccountManagement;

import messaging.implementations.RabbitMqQueue;

public class Main {
    public static void main(String[] args) throws Exception {
        new Main().startUp();
    }

    private void startUp() throws Exception {
        var mq = new RabbitMqQueue("rabbitMq");
        new AccountIdService(mq);
    }
}



