# @Author: Mila (s223313)
# @Author: Adin (s164432)

Feature: Token Generation Feature
  Scenario: Token Generation Success
    Given a customer
    When a "TokenGenerationRequested" event for generating 3 token is received
    Then a "TokenGenerationCompleted" event is sent
    And the customer receives 3 token

  Scenario: Customer requests more tokens than allowed
    Given a customer
    When a "TokenGenerationRequested" event for generating 6 token is received
    Then a "TokenGenerationCompleted" event is sent
    And the customer receives 0 token

  Scenario: Customer requests more tokens than allowed
    Given a customer
    And the customer has 2 token
    When a "TokenGenerationRequested" event for generating 1 token is received
    Then a "TokenGenerationCompleted" event is sent
    And the customer receives 0 token