// @Author: Mila (s223313)

Feature: Token Generation Feature
  Scenario: Token Generation Success
    Given a customer with existing account id
    When a "TokenGenerationRequested" event for generating 1 token is received
    Then a "TokenGenerationSucceeded" event is sent
    And the customer receives a token

  Scenario: Customer requests more tokens than allowed
    Given a customer with existing account id
    When a "TokenGenerationRequested" event for generating 6 tokens is received
    Then a "TokenGenerationFailed" event is sent
    And the customer is notified with "Invalid number of tokens requested" error message

