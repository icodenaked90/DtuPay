package clientApp.models;
import lombok.Data;

@Data
public class MerchantReportEntry {
    Integer amount;
    String token;
    String mid;
    String cid;
}
