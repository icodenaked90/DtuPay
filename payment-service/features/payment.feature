# @Author: Jonathan (s194134)

 Feature: Payment Service Features

   Scenario: Existing customer token and merchant id starts a valid payment
     Given an existing customer with id "cid1" and token "testToken1234"
     And an existing merchant "mid1" bank id "abc123merchant"
     And an existing customer "cid1" bankId with id "abc123customer"

     Given starting a payment from "testToken1234" to "mid1" for 1000 kr

     When a "PaymentRequested" event is received
     Then a "PaymentCompleted" event is sent
     And the payment is successful

   Scenario: Wrong Customer token
     Given an existing customer with id "cid1" and token "wrongToken4321"
     And an existing merchant "mid1" bank id "abc123merchant"
     And an existing customer "cid1" bankId with id "abc123customer"

     Given starting a payment from "testToken1234" to "mid1" for 1000 kr

     When a "PaymentRequested" event is received
     Then a "PaymentCompleted" event is sent
     And the payment is unsuccessful with message "Customer does not have a valid token"
