package org.acme.models;

import lombok.AllArgsConstructor;
import lombok.Data;
//@Author: Jonathan s194134
@Data
@AllArgsConstructor
public class Transaction {
    String customerToken;
    String customerId;
    String merchantId;
    int amount;
}