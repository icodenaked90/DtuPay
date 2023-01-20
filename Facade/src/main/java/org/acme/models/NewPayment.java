package org.acme.models;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;
import lombok.NoArgsConstructor;
//@Author: Jonathan s194134
@XmlRootElement
@Data
@NoArgsConstructor
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
