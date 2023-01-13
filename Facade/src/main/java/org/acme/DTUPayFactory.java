package org.acme;
import messaging.implementations.RabbitMqQueue;

//Author: Adin s164432
public class DTUPayFactory {
    static DTUPayService service = null;
    public synchronized DTUPayService getService(){
        if(service != null){
            return service;
        }
        var mq = new RabbitMqQueue("rabbitMq");
        service = new DTUPayService(mq);
        return service;
    }
}
