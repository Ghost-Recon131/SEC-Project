package rmit.sec.webstorepmicroservice.Account.requests;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class UpdateAccountInfoRequest {
    private String email;
    private String firstname;
    private String lastname;
}
