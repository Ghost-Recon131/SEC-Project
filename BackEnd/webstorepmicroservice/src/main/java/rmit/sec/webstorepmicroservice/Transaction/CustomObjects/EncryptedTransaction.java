package rmit.sec.webstorepmicroservice.Transaction.CustomObjects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class EncryptedTransaction {
    private String transactionID;
    private String itemID;
    private String sellerID;
    private String buyerID;
    private String totalCost;
    private String transactionDate;
}
