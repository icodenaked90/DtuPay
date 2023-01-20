package ReportManagement.model;

import lombok.AllArgsConstructor;
import lombok.Data;
//@Author: jonathan 194134
@Data
@AllArgsConstructor
public class Transaction {
    String customerToken;
    String customerId;
    String merchantId;
    int amount;
}