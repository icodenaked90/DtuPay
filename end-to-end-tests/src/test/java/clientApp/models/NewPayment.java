package clientApp.models;

import lombok.Data;

@Data
public class NewPayment {
//    Payment information
    String customerToken;
    String merchantId;
    int amount;

//    Payment status information
    boolean paymentSuccesful;
    String errorMessage;

    public NewPayment(String customerToken, String merchantId, int amount) {
        this.customerToken = customerToken;
        this.merchantId = merchantId;
        this.amount = amount;
        paymentSuccesful = false;
    }
}
