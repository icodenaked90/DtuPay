package org.acme.models;
import lombok.Data;

@Data
public class CustomerReportEntry {
    Integer amount;
    String token;
    String mid;
}