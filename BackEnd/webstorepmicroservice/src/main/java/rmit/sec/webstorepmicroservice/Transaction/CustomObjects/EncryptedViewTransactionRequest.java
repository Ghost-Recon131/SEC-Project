package rmit.sec.webstorepmicroservice.Transaction.CustomObjects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class EncryptedViewTransactionRequest {
    private String TransactionID;
    private String sellerID;
    private String buyerID;
}
