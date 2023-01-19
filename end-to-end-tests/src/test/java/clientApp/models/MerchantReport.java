package clientApp.models;
import lombok.Data;

import java.util.ArrayList;

@Data
public class MerchantReport {
    ArrayList<MerchantReport> log = new ArrayList<>();
    public void addToLog(MerchantReport reportEntry) {
        log.add(reportEntry);
    }
}
