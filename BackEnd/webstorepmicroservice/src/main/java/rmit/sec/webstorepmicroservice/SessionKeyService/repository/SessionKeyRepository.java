package rmit.sec.webstorepmicroservice.SessionKeyService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rmit.sec.webstorepmicroservice.SessionKeyService.model.SessionKey;

@Repository
public interface SessionKeyRepository extends JpaRepository<SessionKey, Long> {
    SessionKey getBySessionID(Long id);

    SessionKey getSessionKeyByTmpSessionID(Integer tmp);
}
