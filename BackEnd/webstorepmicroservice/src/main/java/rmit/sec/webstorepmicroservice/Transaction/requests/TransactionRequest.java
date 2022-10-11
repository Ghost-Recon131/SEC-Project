package rmit.sec.webstorepmicroservice.Transaction.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TransactionRequest {
    private Long itemID;
    private Long sellerID;
    private Long buyerID;
    private Double totalCost;
}
