#/*
#@Author: Emily s223122
#*/
Feature: Customer Registration feature

  Scenario: Customer Registration Success
  	Given an unregistered customer
  	When the customer is being registered in DTUPay
  	Then the customer receives an DTUPay id

  Scenario: Customer Deregistration Failed
	Given an unregistered customer
	When the customer is being deregistered in DTUPay
	Then the customer receives an error message

  Scenario: Customer Deregistration Success
	Given a registered customer
    When the customer is being deregistered in DTUPay
	Then the customer is deregistered

	#Scenario: Customer Registration Failed - two identical
	#	Given an unregistered customer with firstname "Parker", lastname "Peter", cpr "091276-4322"
	#	And an another unregistered customer with firstname "Nicolas", lastname "Cage", cpr "091276-4422"
	#	When the two students are registered at the same time
	#	Then the first customer has a non empty id
	#	And the second student has a non empty id different from the first student
  	