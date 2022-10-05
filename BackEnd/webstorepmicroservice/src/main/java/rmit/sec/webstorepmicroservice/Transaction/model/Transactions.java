package rmit.sec.webstorepmicroservice.Transaction.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "transactions")
public class Transactions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private Long transactionID;

    @Column(name = "seller_id")
    private Long sellerID;

    @Column(name = "buyer_id")
    private Long buyerID;

    @Column(name = "total_cost")
    private Double totalCost;

}
