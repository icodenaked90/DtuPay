# @Author Mila (s223313)

Feature: Customer Token feature

  Scenario: Customer Token Request Success
  	Given the registered customer with 0 tokens
    When the customer requests 5 tokens
    Then the customer receives 5 tokens
    And the customer owns 5 tokens

  Scenario: Customer Allowed More Tokens Request
    Given the registered customer with 1 tokens
    When the customer requests 2 tokens
    Then the customer receives 2 tokens
    And the customer owns 3 tokens

  Scenario: Invalid Number of Tokens Request
    Given the registered customer with 0 tokens
    When the customer requests 10 tokens
    Then the customer receives the error message "Invalid number of tokens requested."
    And the customer owns 0 tokens

  Scenario: Customer Not Allowed More Tokens Request
    Given the registered customer with 2 tokens
    When the customer requests 3 tokens
    Then the customer receives the error message "User already owns more than one token."
    And the customer owns 2 tokens