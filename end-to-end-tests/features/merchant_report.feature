# @Author: Adin s164432

Feature: Merchant Report feature

 Scenario: Merchant Report Request
    Given a successfully registered report merchant
    When merchant request their report
    Then the merchant receives an empty report

  Scenario: Merchant Report Request empty
    Given an unregistered report merchant
    When merchant request their report
    Then the merchant receives an empty report
