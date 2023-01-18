# @Author Mila (s223313)
# @Author Adin (s164432)

Feature: Payment feature

  Scenario: Payment Success
  	Given the registered customer with 1 tokens with 2000 balance
    And a registered merchant with 30000 balance
    When the merchant requests a payment for 200
    Then the customer owns 0 tokens
    And the customers bank balance  is 1800
    And the merchant bank balance is 30200

  Scenario: Payment Invalid Token Failure
    Given the registered customer with 0 tokens with 2000 balance
    And a registered merchant with 30000 balance
    When the merchant requests a payment for 200
    Then the customer receives the error message "Invalid Token Payment Attempt"
    And the customer owns 0 tokens
    And the customers bank balance  is 2000
    And the merchant bank balance is 30000

  Scenario: Payment Insufficient Balance Failure
    Given the registered customer with 1 tokens with 2000 balance
    And a registered merchant with 30000 balance
    When the merchant requests a payment for 2200
    Then the merchant receives the error message "Insufficient Balance Payment Attempt"
    And the customer has 0 tokens
    And the customers bank balance  is 2000
    And the merchant bank balance is 30000

  Scenario: Payment Unregistered Customer Failure
    Given the unregistered customer with 1 tokens
    And a registered merchant with 30000 balance
    When the merchant requests a payment for 200
    Then the merchant receives the error message "Unregistered Customer Payment Attempt"
    And the customer has 0 tokens
    And the merchant bank balance is 30000

  Scenario: Payment Unregistered Merchant Failure
    Given the registered customer with 1 tokens with 2000 balance
    And a unregistered merchant
    When the merchant requests a payment for 200
    Then the merchant receives the error message "Unregistered Merchant Payment Attempt"
    And the customer has 0 tokens
    And the customers bank balance  is 2000