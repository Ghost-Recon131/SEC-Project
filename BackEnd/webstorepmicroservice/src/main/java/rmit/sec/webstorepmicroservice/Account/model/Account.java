package rmit.sec.webstorepmicroservice.Account.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import rmit.sec.webstorepmicroservice.utils.AccountRole;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;


@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Entity
@Table(name = "Account")
public class Account implements UserDetails{

    // SQL primary key
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "email")
    private String email;

    @Column(name = "firstname")
    private String firstname;

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "password")
    private String password;

    @Column(name = "secret_question")
    private String secretQuestion;

    @Column(name = "secret_question_answer")
    private String secretQuestionAnswer;

    @Column(name = "uuid")
    private String uuid;

    // Stores the role associated with the account
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private AccountRole accountRole;

    // Do not lock account on creation
    @Column(name = "lock_status")
    private Boolean locked = false;

    // Allow account to be accessed on creation
    @Column(name = "account_enabled")
    private Boolean enabled = true;

    // Store account creation date
    @Column(name = "create_At")
    private Date create_At = new Date();

    // Store last time the account was updated
    @Column(name = "update_At")
    private Date update_At;

    // Constructor for a new Account
    public Account(String username, String email, String firstname, String lastname, String password,
                   String secretQuestion, String secretQuestionAnswer, String uuid, AccountRole accountRole) {
        this.username = username;
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.password = password;
        this.secretQuestion = secretQuestion;
        this.secretQuestionAnswer = secretQuestionAnswer;
        this.uuid = uuid;
        this.accountRole = accountRole;
    }

    // ----------------------------------------------------------------
    // Methods implemented from UserDetails interface, can ignore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(accountRole.name());
        return Collections.singletonList(authority);
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
    // ----------------------------------------------------------------
}
