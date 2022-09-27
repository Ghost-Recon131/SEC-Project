package rmit.sec.webstorepmicroservice.Account.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rmit.sec.webstorepmicroservice.Account.model.Account;


import javax.persistence.OneToMany;
import javax.transaction.Transactional;

// Automate SQL statements
@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    @Transactional
    Account getAccountByUsername(String username);

    Account getAccountByEmail(String email);

    Account getById(Long id);

    // Delete all other data entries related to this user
    @OneToMany
    void deleteById(Long id);
}
