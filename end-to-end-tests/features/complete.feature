# @Author Mila (s223313)
# @Author Hildibjørg (s164539)

Feature: Complete System feature

  Scenario: Customer Registration Success
  	Given an registered complete customer
  	And an registered complete merchant
  	Then complete customer request a token
  	And complete merchant receives payment
  	And complete merchant request log
	Then log contains payment
