package rmit.sec.webstorepmicroservice.Account.requests;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class UpdateAccountInfoRequest {
    private String email;
    private String firstname;
    private String lastname;
}
