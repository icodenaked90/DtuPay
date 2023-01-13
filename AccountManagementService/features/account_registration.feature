/* @Author: Mila (s223313)

*/
Feature: Account Registration Feature
  Scenario: Account Registration Success
    Given a user with an empty account id
    When a "AccountRegistrationRequested" event is received
    Then a "AccountRegistrationSucceeded" event is sent
    And the customer receives an account id