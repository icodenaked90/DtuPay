#   @Author: Mila (s223313)
#   @Author: Hildibjørg (s164539)

Feature: Account Registration Feature
  Scenario: Scenario Account Registration Success
    Given there is an account with an empty id
    When a "AccountRegistrationRequested" for generating account is received
    And the account is registered
