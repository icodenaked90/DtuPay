# @Author: Adin

Feature: Merchant Report feature

  Scenario: Merchant Report Request
    Given a successfully registered report merchant
    When merchant request their report
    Then the merchant report is received

  Scenario: Merchant Report Request empty
    Given an unregistered report merchant
    When merchant request their report
    Then the merchant receives an empty report
