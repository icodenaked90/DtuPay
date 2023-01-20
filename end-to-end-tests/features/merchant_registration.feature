# @Author Mila (s223313)
# @Author Hildibjørg (s164539)

Feature: Merchant Registration feature

  Scenario: Merchant Registration Success
    Given an unregistered merchant
    When the merchant is being registered in DTUPay
    Then the merchant receives a DTUPay id

  Scenario: Merchant Registration Failure
    Given an unregistered merchant
    When the merchant has an invalid CPR when being registered in DTUPay
    Then the merchant receives an error message in registration

  Scenario: Merchant Registration Failure 2
    Given an unregistered merchant
    When the merchant has an invalid name when being registered in DTUPay
    Then the merchant receives an error message in registration

  Scenario: Merchant Deregistration Failed
    Given an unregistered merchant
    When the merchant is being deregistered in DTUPay
    Then the merchant receives an error message

  Scenario: Merchant Deregistration Success
    Given a registered merchant
    When the merchant is being deregistered in DTUPay
    Then the merchant is deregistered
