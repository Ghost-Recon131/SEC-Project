package rmit.sec.webstorepmicroservice.SessionKeyService.requests;

import lombok.*;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class EncryptedDataRequest {
    private Long sessionID;
    private String encryptedData;
}
