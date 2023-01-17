#/*
#@Author: Emily s223122
#*/
Feature: Customer Report feature

  Scenario: Customer Report Request - Empty
    Given a successfully registered report customer
    When customer request their report
    Then the report is received

  Scenario: Customer Report Request Fail - Unregistered Customer
    Given an unregistered report customer
    When customer request their report
    Then the customer receives a report error message