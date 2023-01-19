package ReportManagement.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Transaction {
    String customerToken;
    String customerId;
    String merchantId;
    int amount;
}