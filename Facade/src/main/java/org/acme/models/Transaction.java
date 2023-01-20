package org.acme.models;

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