/*
This file is copied from the "Correlation Student Registration Example" zip file.
Created by Hubert Baumeister.
Accessed on 2023-01-11
And has been adjusted to the AccountManagementService
*/

package ReportManagement;

import ReportManagement.ReportService;
import messaging.implementations.RabbitMqQueue;

public class StartUp {
    public static void main(String[] args) throws Exception {
        new StartUp().startUp();
    }

    private void startUp() throws Exception {
        var mq = new RabbitMqQueue("rabbitMq");
        new ReportService(mq);
    }
}



