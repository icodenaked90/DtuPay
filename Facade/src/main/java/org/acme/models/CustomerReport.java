package org.acme.models;

import lombok.Data;

import java.util.ArrayList;
//@Author: Emily s223122
@Data
public class CustomerReport {
    ArrayList<CustomerReportEntry> log = new ArrayList<>();
    public void addToLog(CustomerReportEntry reportEntry) {
        log.add(reportEntry);
    }
}
