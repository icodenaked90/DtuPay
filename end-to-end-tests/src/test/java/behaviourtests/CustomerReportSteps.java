/*
@Author: Mila (s223313)
@Author: Adin (s164432)
@Author: Emily s223122
 */

package behaviourtests;
import clientApp.CustomerAppService;
import clientApp.models.Account;
import clientApp.models.MerchantReport;
import dtu.ws.fastmoney.BankService;
import dtu.ws.fastmoney.BankServiceException_Exception;
import dtu.ws.fastmoney.BankServiceService;
import dtu.ws.fastmoney.User;
import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.math.BigDecimal;
import static org.junit.Assert.*;


public class CustomerReportSteps {
    private Account customer;
    private User bankCustomer = new User();
    private String report = "";
    private String cAccount;
    private CustomerAppService customerApp= new CustomerAppService();
    BankService bank = new BankServiceService().getBankServicePort();
    private String cid = "";

    @Given("a successfully registered report customer")
    public void aSuccessfullyRegisteredReportCustomer() {
        bankCustomer.setFirstName("Decent");
        bankCustomer.setLastName("Merchant");
        String cpr = CprGenerator.generate();
        bankCustomer.setCprNumber(cpr);
        try {
            cAccount = bank.createAccountWithBalance(bankCustomer, BigDecimal.valueOf(2000));
        } catch (BankServiceException_Exception e) {
            fail("Invalid customer bank account.");
        }
        customer = new Account("Good Customer", cpr, cAccount);
        cid = customerApp.register(customer);
    }

    @When("customer request their report")
    public void customerRequestTheirReport() {
        report = customerApp.getReport(cid);
    }

    @Then("the customer receives an empty report")
    public void theCustomerReceivesAnEmptyReport() {
        MerchantReport expectedReport = new MerchantReport();
        assertEquals(report, expectedReport);
    }

    @Given("an unregistered report customer")
    public void anUnregisteredReportCustomer() {
        cid = "unregisteredCustomer";
    }

    @After()
    public void Cleanup()
    {
        if (cAccount != null) {
            try {
                bank.retireAccount(cAccount);
                cAccount = null;
            } catch (BankServiceException_Exception e) {
                fail("Failed cleanup.");
            }
        }
    }
}
