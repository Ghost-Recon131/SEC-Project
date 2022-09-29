package rmit.sec.webstorepmicroservice.SessionKeyService.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigInteger;
import java.time.LocalDate;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Entity
@Table(name = "Session_Key")
public class SessionKey {

    // SQL primary key
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sessionkey_id")
    private Long sessionID;
    @Column(name = "session_key")
    private String sessionKey;

    // Date created
    @Column(name = "creation_date")
    private LocalDate creationDate = LocalDate.now();

    // Expiry date, set to 1 day from creation
    @Column(name = "expiry_date")
    private LocalDate expiryDate = creationDate.plusDays(1);

    // Constructor
    public SessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }

}
