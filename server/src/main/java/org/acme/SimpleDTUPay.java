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

@XmlRootElement // Needed for XML serialization and deserialization
@Data // Automatic getter and setters and equals etc
@NoArgsConstructor // Needed for JSON deserialization and XML serialization and deserialization
@AllArgsConstructor
public class SimpleDTUPay {
    private String cid = "cid1";
    private String mid = "mid1";

    private ArrayList<PaymentLogEntry> paymentLog = new ArrayList<PaymentLogEntry>();

    private BankService bank = new BankServiceService().getBankServicePort();

    public void pay(int amount, String cid, String mid) {//throws BankServiceException_Exception {
        //bank.transferMoneyFromTo(cid, mid, BigDecimal.valueOf(amount), "DTUPay");


        // check for cid
        if (!Objects.equals(cid, this.cid)) {
            throw new NotFoundException("customer with id " + cid + " is unknown");
        }

        // check for mid
        if (!Objects.equals(mid, this.mid)) {
            throw new NotFoundException("merchant with id " + mid + " is unknown");
        }

        paymentLog.add(new PaymentLogEntry(amount, cid, mid));

    }
}
