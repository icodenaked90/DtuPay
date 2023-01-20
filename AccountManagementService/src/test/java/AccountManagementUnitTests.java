import AccountManagement.models.Account;
import AccountManagement.AccountIdService;
import messaging.MessageQueue;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;

//@Author Hildibjørg s164539
public class AccountManagementUnitTests {

    @Test
    public void checkCprNumber(){

        MessageQueue queue = mock(MessageQueue.class);
        AccountIdService service = new AccountIdService(queue);

        Account wrongAccountInfo = new Account();
        wrongAccountInfo.setCPR("a123456789");
        wrongAccountInfo.setName("Peter Mikkelsen");
        wrongAccountInfo.setBankAccount("1234567890");
        String accountId = service.register(wrongAccountInfo);

        assertEquals(accountId, "CRP number has a wrong format");
    }

    @Test
    public void wrongLengthCprNumber(){
        MessageQueue queue = mock(MessageQueue.class);
        AccountIdService service = new AccountIdService(queue);

        Account wrongAccountInfo = new Account();
        wrongAccountInfo.setCPR("123");
        wrongAccountInfo.setName("Peter Mikkelsen");
        wrongAccountInfo.setBankAccount("1234567890");
        String accountId = service.register(wrongAccountInfo);

        assertEquals(accountId, "CRP number has a wrong format");
    }
    @Test
    public void checkCorrectCprNumber(){

        MessageQueue queue = mock(MessageQueue.class);
        AccountIdService service = new AccountIdService(queue);

        Account correctAccountInfo = new Account();
        correctAccountInfo.setCPR("0123456789");
        correctAccountInfo.setName("Peter Mikkelsen");
        correctAccountInfo.setBankAccount("1234567890");
        String accountId = service.register(correctAccountInfo);

        assertNotNull(accountId);
    }
    @Test
    public void checkWrongCprNumber2(){

        MessageQueue queue = mock(MessageQueue.class);
        AccountIdService service = new AccountIdService(queue);

        Account correctAccountInfo = new Account();
        correctAccountInfo.setCPR("012345-67891");
        correctAccountInfo.setName("Peter Mikkelsen");
        correctAccountInfo.setBankAccount("1234567890");
        String accountId = service.register(correctAccountInfo);

        assertEquals(accountId, "CRP number has a wrong format");
    }
    @Test
    public void checkCorrectCprNumber2(){

        MessageQueue queue = mock(MessageQueue.class);
        AccountIdService service = new AccountIdService(queue);

        Account correctAccountInfo = new Account();
        correctAccountInfo.setCPR("012345-6789");
        correctAccountInfo.setName("Peter Mikkelsen");
        correctAccountInfo.setBankAccount("1234567890");
        String accountId = service.register(correctAccountInfo);

        assertNotNull(accountId);
    }
    @Test
    public void checkForEmptyName(){
        MessageQueue queue = mock(MessageQueue.class);
        AccountIdService service = new AccountIdService(queue);

        Account wrongAccountInfo = new Account();
        wrongAccountInfo.setCPR("0123456789");
        wrongAccountInfo.setName("");
        wrongAccountInfo.setBankAccount("1234567890");
        String accountId = service.register(wrongAccountInfo);

        assertEquals(accountId, "Name has a wrong format");
    }
}
