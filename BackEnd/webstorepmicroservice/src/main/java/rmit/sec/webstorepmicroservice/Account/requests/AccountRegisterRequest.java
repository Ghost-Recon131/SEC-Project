package rmit.sec.webstorepmicroservice.Account.requests;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class AccountRegisterRequest {
    private String username;
    private String email;
    private String firstname;
    private String lastname;
    private String password;
    private String secret_question;
    private String secret_question_answer;
}
