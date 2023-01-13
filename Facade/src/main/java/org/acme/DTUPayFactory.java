package org.acme;
//Author: Adin s164432
public class DTUPayFactory {
    static DTUPayService service = null;
    public synchronized DTUPayService getService(){
        if(service != null){
            return service;
        }
        var mq = new RabbitMQueue("rabbitMq");
        service = new DTUPayService(mq);
        return service;
    }
}
