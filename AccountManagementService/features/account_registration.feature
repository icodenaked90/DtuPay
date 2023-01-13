/* @Author: Mila (s223313)
   @Author: Hildibjørg (s164539)
*/
Feature: Account Registration Feature
  Scenario: Account Registration Success
    Given a user with an empty account id
    When a "AccountRegistrationRequested" event is received
    Then a "AccountIdAssigned" event is sent
    And the customer receives an account id