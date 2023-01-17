#/*
#@Author: Emily s223122
#*/
Feature: Customer Token feature

  Scenario: Customer Token Request - One Token
    Given a successfully registered customer
    When request 1 tokens
    Then then 1 tokens is received

  Scenario: Customer Token Request - Some Tokens
    Given a successfully registered customer
  	When request 3 tokens
    Then then 3 tokens is received

  Scenario: Customer Token Request Failed - Too Many
    Given a successfully registered customer
    When request 7 tokens
    Then the customer receives a token error message

  Scenario: Customer Token Request Failed - Negative
    Given a successfully registered customer
    When request -1 tokens
    Then the customer receives a token error message

  Scenario: Customer Token Request - Unregistered Customer
    Given a unregistered customer
    When request 1 tokens
    Then the customer receives a token error message