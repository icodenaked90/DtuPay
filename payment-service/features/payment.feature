# @Author: Jonathan (s194134)

 Feature: Payment Service Features

   Scenario: Existing customer token and merchant id starts a valid payment
     Given a customer with token "testToken1234"
     And a merchant with id "merchantId1"
     And a payment amount of 12345
     When a "PaymentRequested" event is received
     Then a "PaymentCompleted" event is sent
     And the payment is successful
