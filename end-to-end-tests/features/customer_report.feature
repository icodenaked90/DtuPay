
# @Author: Emily s223122
# @Author: Mila
# @Author: Adin

Feature: Customer Report feature

  Scenario: Customer Report Request
    Given a successfully registered report customer
    When customer request their report
    Then the report is received

  Scenario: Customer Report Request empty
    Given an unregistered report customer
    When customer request their report
    Then the customer receives an empty report
