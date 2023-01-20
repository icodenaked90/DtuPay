# @Author Emily (s223122)

Feature: Complete System feature

  Scenario: Customer Registration Success
  	Given an registered complete customer
  	And an registered complete merchant
  	Then complete customer request a token
  	And complete merchant receives payment
  	And complete merchant request log
	Then log contains payment
