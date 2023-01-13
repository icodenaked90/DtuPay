package behaviourtests;

import clientApp.MerchantAppService;
import clientApp.models.Account;
import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.wildfly.common.Assert.assertTrue;

public class MerchantRegistrationSteps {
    private clientApp.models.Account merchant;
    private String mid = "";
    private MerchantAppService merchantClient = new MerchantAppService();

    @Given("an unregistered merchant with empty id")
    public void anUnregisteredMerchantWithEmptyId() {
        this.merchant = new Account("John", "123456-1234", "1234");
    }

    @When("the merchant is being registered")
    public void theMerchantIsBeingRegistered() {
        this.mid = merchantClient.registerNewMerchantAccount(merchant);
        System.out.println(mid);
    }

    @Then("the merchant is registered")
    public void theMerchantIsRegistered() {
        assertTrue(this.mid != "");
    }

    @And("has a non empty id")
    public void hasANonEmptyId() {
        assertTrue(this.mid != "");
    }
/*
    @After
    public void Cleanup()
    {
        if (cid != null) {
            public void Cleanup() {
                if(cAccount != null){
                    try {
                        bank.retireAccount(cid);
                        bank.retireAccount(cAccount);
                    } catch (BankServiceException_Exception e) {

                        fail("Failed cleanup cAc):");
                    }
                }
                if (mid != null) {
                    if(mAccount != null){
                        try {
                            bank.retireAccount(mid);
                            bank.retireAccount(mAccount);
                        } catch (BankServiceException_Exception e) {

                            fail("Failed cleanup ):");
                        }
                    }
                }
                */
}
