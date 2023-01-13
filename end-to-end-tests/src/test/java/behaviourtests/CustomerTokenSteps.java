package behaviourtests;
import clientApp.CustomerAppService;
import io.cucumber.java.en.Given;

public class CustomerTokenSteps {
    CustomerAppService c = new CustomerAppService();
    @Given("request token")
    public void requestToken() {
        System.out.println(c.getToken("test cid", 5));

    }
}
