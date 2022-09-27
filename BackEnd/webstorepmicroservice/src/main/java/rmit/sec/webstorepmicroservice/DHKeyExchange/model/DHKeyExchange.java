package rmit.sec.webstorepmicroservice.DHKeyExchange.model;

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
@Table(name = "DHKeyExchange")
public class DHKeyExchange {

    // SQL primary key
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sessionkey_id")
    private Long sessionID;

    // modulus value 'P'
    @Column(name = "p")
    private BigInteger publicKey;

    // primitive root modulo of 'P'
    @Column(name = "g")
    private BigInteger publicKeyRootModulus;

    // Secret integer 'A' for server
    @Column(name = "a")
    private BigInteger serverSecretKey;

    // Established session key
    @Column(name = "session_key")
    private BigInteger sessionKey;

    // Date created
    @Column(name = "creation_date")
    private LocalDate creationDate = LocalDate.now();

    // Expiry date, set to 1 day from creation
    @Column(name = "expiry_date")
    private LocalDate expiryDate = creationDate.plusDays(1);

    // Constructor
    public DHKeyExchange(BigInteger publicKey, BigInteger publicKeyRootModulus, BigInteger serverSecretKey, BigInteger sessionKey) {
        this.publicKey = publicKey;
        this.publicKeyRootModulus = publicKeyRootModulus;
        this.serverSecretKey = serverSecretKey;
        this.sessionKey = sessionKey;
    }

}
