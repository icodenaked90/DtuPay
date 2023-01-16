package Payment;

import lombok.Data;

@Data
public class NewPayment {
//    Payment information
    String customerToken;
    String merchantID;
    int Amount;
//    Payment status information
    boolean status;
    String errorMessage;

}
