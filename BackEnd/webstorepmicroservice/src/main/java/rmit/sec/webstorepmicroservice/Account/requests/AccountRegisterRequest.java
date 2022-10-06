package rmit.sec.webstorepmicroservice.Account.requests;

import lombok.*;

@Getter
@Setter
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
