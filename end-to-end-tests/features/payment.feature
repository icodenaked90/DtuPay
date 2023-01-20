# @Author Mila (s223313)

Feature: Payment feature

  Scenario: Payment Success
  	Given the registered customer with 1 tokens with 2000 balance
    And a registered merchant with 30000 balance
    When the merchant requests a payment for 200
    And customer pays with an unused token
    Then the payment succeeds
    And the customers bank balance is 1800
    And the merchant bank balance is 30200

  Scenario: Payment Reuse Token Failure
    Given the registered customer with 1 tokens with 2000 balance
    And a registered merchant with 30000 balance
    When the merchant requests a payment for 200
    And customer pays with an unused token
    Then the payment succeeds
    And the customers bank balance is 1800
    And the merchant bank balance is 30200
    When the merchant requests a payment for 500
    And customer pays with the previous token
    Then the merchant receives the error message "Customer does not have a valid token"
    And the customers bank balance is 1800
    And the merchant bank balance is 30200

  Scenario: Payment Insufficient Balance Failure
    Given the registered customer with 1 tokens with 2000 balance
    And a registered merchant with 30000 balance
    When the merchant requests a payment for 2200
    And customer pays with an unused token
    Then the merchant receives the error message "Debtor balance will be negative"
    And the customers bank balance is 2000
    And the merchant bank balance is 30000

  Scenario: Payment Unregistered Customer Failure
    Given the unregistered customer
    And a registered merchant with 30000 balance
    When the merchant requests a payment for 200
    And customer pays with a fake token
    Then the merchant receives the error message "Customer does not have a valid token"
    And the merchant bank balance is 30000

  Scenario: Payment Unregistered Merchant Failure
    Given the registered customer with 1 tokens with 2000 balance
    And a unregistered merchant
    When the merchant requests a payment for 200
    And customer pays with an unused token
    Then the merchant receives the error message "Merchant does not have a valid bank account"
    And the customers bank balance is 2000