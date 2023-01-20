# @Author: Jonathan (s194134)

Feature: Payment Service Features

  Scenario: Existing customer token and merchant id starts a valid payment
    # Set state of test stubs, token and account service
    Given an existing customer with id "cid1" and token "testToken1234"
    And an existing merchant "mid1" bank id "abc123merchant"
    And an existing customer "cid1" bankId with id "abc123customer"

    # Start a new payment
    Given starting a payment from "testToken1234" to "mid1" for 1000 kr
    # Control payment
    When a "PaymentRequested" event is received
    Then a "PaymentCompleted" event is sent
    And the payment is successful

  Scenario: Wrong Customer token
    #     Set state of test stubs, token and account service
    Given an existing customer with id "cidNew1" and token "wrongToken4321"
    And an existing merchant "mid1" bank id "abc123merchant"
    And an existing customer "cidNew1" bankId with id "abc123customer"

    #     Start a new payment
    Given starting a payment from "testToken341234" to "mid1" for 1000 kr
    #    Control payment
    When a "PaymentRequested" event is received
    Then a "PaymentCompleted" event is sent
    And the payment is unsuccessful with message "Customer does not have a valid token"
