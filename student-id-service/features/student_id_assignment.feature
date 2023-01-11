Feature: Student Id Assignment feature

  Scenario: Student Id Assignment
    When a "StudentRegistrationRequested" event for a student is received
    Then the "StudentIdAssigned" event is sent with the same correlation id
    And the student gets a student id
  