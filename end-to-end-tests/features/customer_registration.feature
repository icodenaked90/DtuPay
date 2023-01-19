# @Author Mila (s223313)
# @Author Hildibjørg (s164539)

Feature: Customer Registration feature

  Scenario: Customer Registration Success
  	Given an unregistered customer
  	When the customer is being registered in DTUPay
  	Then the customer receives a DTUPay id

  Scenario: Customer Registration Failure
	Given an unregistered customer
	When the customer has an invalid CPR when being registered in DTUPay
	Then the customer receives an error message in registration

	Scenario: Customer Registration Failure 2
	Given an unregistered customer
	When the customer has an invalid name when being registered in DTUPay
	Then the customer receives an error message in registration

  Scenario: Customer Deregistration Failed
	Given an unregistered customer
	When the customer is being deregistered in DTUPay
	Then the customer receives an error message

  Scenario: Customer Deregistration Success
	Given a registered customer
    When the customer is being deregistered in DTUPay
	Then the customer is deregistered
