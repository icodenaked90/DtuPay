#   @Author: Mila (s223313)
#   @Author: Hildibjørg (s164539)

Feature: Account Registration Feature
  Scenario: Scenario Account Registration Success
    Given there is an account with an empty id
    When a "AccountRegistrationRequested" for generating account is received
    And the account is registered

  Scenario: Account Deregistration Success
    Given there is an preexisting account with an id
    When a "AccountDeregistrationRequested" for deleting an account is received
    And the account is deregistered

    Scenario: Account Registration name failure
      Given there is an account with an empty name and no id
      When a "AccountRegistrationRequested" for generating account is received
      And the account is not registered

  Scenario: Account Registration CPR failure
    Given there is an account with an invalid CPR number and no id
    When a "AccountRegistrationRequested" for generating account is received
    And the account is not registered