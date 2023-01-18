# @Author Mila (s223313)

Feature: Payment feature

  Scenario: Payment Success
  	Given the registered customer in DTUPay
    And the registered merchant in DTUPay
    When the merchant requests a payment
    And the customer bank account gets validated
    And the merchant bank account gets validated
    Then the customer's token gets accepted and consumed
    And the customer pays the amount
    And the merchant receives the amount

  Scenario: Payment Invalid Token Failure
    Given the registered customer in DTUPay
    And the registered merchant in DTUPay
    And the customer bank account gets validated
    And the merchant bank account gets validated
    Then the customer's token gets rejected
    And the customer fails to pay the amount
    And the merchant fails to receive the amount

  Scenario: Payment Insufficient Balance Failure
    Given the registered customer in DTUPay
    And the registered merchant in DTUPay
    When the merchant requests a payment
    And the customer bank account gets validated
    And the merchant bank account gets validated
    Then the customer's token gets accepted and consumed
    And the customer fails to pay the amount
    And the merchant fails to receive the amount
