package rmit.sec.webstorepmicroservice.Transaction.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rmit.sec.webstorepmicroservice.Transaction.model.Transactions;

@Repository
public interface TransactionRepository extends JpaRepository<Transactions, Long> {

    //TODO

}
