package org.acme.models;

import lombok.Value;

import java.util.UUID;
/*
This file is copied from the "Correlation Student Registration Example" zip file.
Created by Hubert Baumeister.
Accessed on 2023-01-11
And has been adjusted to the AccountManagementService
*/

@Value
public class CorrelationId {
    private UUID id;

    public static CorrelationId randomId() {
        return new CorrelationId(UUID.randomUUID());
    }
}