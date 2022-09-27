package rmit.sec.webstorepmicroservice.DHKeyExchange.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rmit.sec.webstorepmicroservice.DHKeyExchange.model.DHKeyExchange;

@Repository
public interface DHKeyExchangeRepository extends JpaRepository<DHKeyExchange, Long> {
    DHKeyExchange getBySessionID(Long id);
}
