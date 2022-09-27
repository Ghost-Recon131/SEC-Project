package rmit.sec.webstorepmicroservice.DHKeyExchange.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigInteger;

@Getter
@AllArgsConstructor
public class ClientCalculatedValue {
    private Long sessionID;
    private BigInteger clientValue;
}
