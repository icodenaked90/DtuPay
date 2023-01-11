Feature: Student registration feature

  Scenario: Student registration
  	Given there is a student with empty id
  	When the student is being registered
  	Then the "StudentRegistrationRequested" event is published
  	When the StudentIdAssigned event is received with non-empty id
  	Then the student is registered and his id is set
  	
  Scenario: Student registration interleaving requests
  	Given there is a student with empty id
  	When the student is being registered
  	Then the "StudentRegistrationRequested" event is published for the first student
  	Given another student with empty id
  	When the second student is being registered
  	Then the "StudentRegistrationRequested" event is published for the second student
  	When the StudentIdAssigned event is received for the second student
  	Then the second student is registered and his id is set
  	When the StudentIdAssigned event is received for the first student
  	Then the first student is registered and his id is set
  