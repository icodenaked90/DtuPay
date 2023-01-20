//	@Author: Jonathan (s194134)

package Payment;

import dtu.ws.fastmoney.BankService;
import dtu.ws.fastmoney.BankServiceService;

import messaging.implementations.RabbitMqQueue;

public class PaymentFactory {
    // @Author: Jonathan (s194134)
    static PaymentService service = null;

    public synchronized PaymentService getService() {
        if (service != null) {
            return service;
        }
        var mq = new RabbitMqQueue("rabbitMq");
        BankService b = new BankServiceService().getBankServicePort();
        service = new PaymentService(mq, b);
        return service;
    }
}
