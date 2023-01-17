# @Author Mila (s223313)

Feature: Merchant Registration feature

  Scenario: Merchant Registration Success
  	Given an unregistered merchant
  	When the merchant is being registered in DTUPay
  	Then the merchant receives a DTUPay id

  Scenario: Merchant Deregistration Failed
	Given an unregistered merchant
	When the merchant is being deregistered in DTUPay
	Then the merchant receives an error message

  Scenario: Merchant Deregistration Success
	Given a registered merchant
	When the merchant is being deregistered in DTUPay
	Then the merchant is deregistered

#  Scenario: Student Registration Race Condition
#  	Given an unregistered student with empty id
#  	And another unregistered student
#  	When the two students are registered at the same time
#  	Then the first student has a non empty id
#  	And the second student has a non empty id different from the first student
  	