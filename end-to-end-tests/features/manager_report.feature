#@Author: Emily s223122

Feature: Manager Report feature

  Scenario: Manager Report Request
    Given a registered manager
    When manager request their report
     Then the manager report is received