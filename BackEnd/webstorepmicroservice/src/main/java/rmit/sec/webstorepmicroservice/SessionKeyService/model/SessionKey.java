package rmit.sec.webstorepmicroservice.SessionKeyService.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

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
    @Column(name = "session_key", length = 10000)
    private String sessionKey;

    // Used to keep track of initial key exchange, no use beyond that
    @Column(name = "tmp_sessionID")
    private Integer tmpSessionID;

    // Date created
    @Column(name = "creation_date")
    private LocalDateTime creationDate = LocalDateTime.now();

    // Expiry date, set to 1 day from creation
    @Column(name = "expiry_date")
    private LocalDateTime expiryDate = creationDate.plusHours(24);

    // Constructor
    public SessionKey(String sessionKey, Integer tmpSessionID) {
        this.sessionKey = sessionKey;
        this.tmpSessionID = tmpSessionID;
    }

}
