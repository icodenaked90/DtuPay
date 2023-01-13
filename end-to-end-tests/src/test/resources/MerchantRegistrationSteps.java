import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import models.Account;

public class MerchantRegistrationSteps {
    Account merchant;




    @Given("an unregistered merchant with empty id")
    public void anUnregisteredMerchantWithEmptyId() {
        merchant = new Account("John", "123456-1234", "")
    }

    @When("the merchant is being registered")
    public void theMerchantIsBeingRegistered() {
    }

    @Then("the merchant is registered")
    public void theMerchantIsRegistered() {
    }

    @And("has a non empty id")
    public void hasANonEmptyId() {
    }
}
