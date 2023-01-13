Feature: Merchant Registration feature

  Scenario: Merchant Registration
  	Given an unregistered merchant with empty id
  	When the merchant is being registered
  	Then the merchant is registered
  	And has a non empty id  	

#  Scenario: Student Registration Race Condition
#  	Given an unregistered student with empty id
#  	And another unregistered student
#  	When the two students are registered at the same time
#  	Then the first student has a non empty id
#  	And the second student has a non empty id different from the first student
  	