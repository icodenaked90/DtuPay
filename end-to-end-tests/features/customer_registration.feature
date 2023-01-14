Feature: Customer Registration feature

  Scenario: Customer Registration Success
  	Given an unregistered customer
  	When the customer is being registered in DTUPay
  	Then the customer receives an DTUPay id

#  Scenario: Student Registration Race Condition
#  	Given an unregistered student with empty id
#  	And another unregistered student
#  	When the two students are registered at the same time
#  	Then the first student has a non empty id
#  	And the second student has a non empty id different from the first student
  	