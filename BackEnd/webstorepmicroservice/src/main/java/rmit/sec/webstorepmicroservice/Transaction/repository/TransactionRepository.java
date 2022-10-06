package rmit.sec.webstorepmicroservice.Transaction.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rmit.sec.webstorepmicroservice.Transaction.model.Transactions;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transactions, Long> {

    // Get a particular transaction
    Transactions getByTransactionID(Long transactionID);

    // Get all sales made by a user
    List<Transactions> getAllBySellerID(Long sellerID);

    // Get all purchases for a user
    List<Transactions> getAllByBuyerID(Long buyerID);

}
