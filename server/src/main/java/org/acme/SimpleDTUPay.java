package org.acme;

import javax.ws.rs.NotFoundException;
import javax.xml.bind.annotation.XmlRootElement;

import dtu.ws.fastmoney.Account;
import dtu.ws.fastmoney.BankService;
import dtu.ws.fastmoney.BankServiceException_Exception;
import dtu.ws.fastmoney.BankServiceService;
import io.cucumber.java.mk_latn.No;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Objects;
import java.util.HashMap;

import java.util.UUID;

@XmlRootElement // Needed for XML serialization and deserialization
@Data // Automatic getter and setters and equals etc
@NoArgsConstructor // Needed for JSON deserialization and XML serialization and deserialization
@AllArgsConstructor
public class SimpleDTUPay {
    private HashMap<String, String> userAccounts = new HashMap<String, String>();

    private ArrayList<PaymentLogEntry> paymentLog = new ArrayList<PaymentLogEntry>();

    private BankService bank = new BankServiceService().getBankServicePort();

    public void pay(int amount, String cid, String mid) throws BankServiceException_Exception {
        // check for cid
        if (!userAccounts.containsKey(cid)) {
            throw new NotFoundException("customer with id " + cid + " is unknown");
        }

        // check for mid
        if (!userAccounts.containsKey(mid)) {
            throw new NotFoundException("merchant with id " + mid + " is unknown");
        }

        bank.transferMoneyFromTo(userAccounts.get(cid), userAccounts.get(mid), BigDecimal.valueOf(amount), "DTUPay");

        paymentLog.add(new PaymentLogEntry(amount, cid, mid));
    }

    // returns the id of the customer and merchant which are assigned by DTU Pay
    public String register(String name, String CPR, String bankAccount) {
        String id = generateId();
        userAccounts.put(id,bankAccount);
        return id;
    }

    private String generateId(){
        while(true) {
            String uuid = UUID.randomUUID().toString();
            if (!userAccounts.containsKey(uuid)) return uuid;
        }
    }
}
