# @Author: Adin (s164432)

Feature: Token Validation Feature
  Scenario: Token Validation Success
    Given a customer
    And the customer has 1 token
    When a "TokenValidationRequested" event is received with the existing token
    Then a "TokenValidationCompleted" event is sent
    And the customer has 0 token

  Scenario: Token Validation failed
    Given a customer
    And the customer has 1 token
    When a "TokenValidationRequested" event is received with a fake token
    Then a "TokenValidationCompleted" event is sent
    And the customer has 1 token
