package rmit.sec.webstorepmicroservice.Account.requests;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class ForgotPasswordRequest {
    private String username;
    private String secret_question;
    private String secret_question_answer;
    private String newPassword;
}
