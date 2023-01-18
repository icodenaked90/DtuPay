# @Author Mila (s223313)

Feature: Customer Token feature

  Scenario: Customer Token Request Success
  	Given the registered customer with 0 tokens
    When the customer requests 5 tokens
    Then the customer receives 5 tokens
    And the customer owns 5 tokens
