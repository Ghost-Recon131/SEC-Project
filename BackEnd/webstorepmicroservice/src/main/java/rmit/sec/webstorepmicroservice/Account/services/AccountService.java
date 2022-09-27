package rmit.sec.webstorepmicroservice.Account.services;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rmit.sec.webstorepmicroservice.Account.model.Account;
import rmit.sec.webstorepmicroservice.Account.repository.AccountRepository;
import rmit.sec.webstorepmicroservice.Account.requests.AccountRegisterRequest;
import rmit.sec.webstorepmicroservice.Account.requests.LoginRequest;
import rmit.sec.webstorepmicroservice.security.JWTLoginSucessReponse;
import rmit.sec.webstorepmicroservice.security.JwtTokenProvider;
import rmit.sec.webstorepmicroservice.utils.AccountRole;

import java.util.UUID;

import static rmit.sec.webstorepmicroservice.security.SecurityConstant.TOKEN_PREFIX;

@Service
@NoArgsConstructor
public class AccountService implements UserDetailsService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private final Logger logger = LogManager.getLogger(this.getClass());

    // Method inherited from UserDetailsService, do not change!!!
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return accountRepository.getAccountByUsername(username);
    }

    // Used to retrieve account in JWT authentication filter, do not change!!!
    @Transactional
    public Account loadUserById(Long id){
        return accountRepository.getById(id);
    }

    // Determine if an account exists by checking email address against DB
    public boolean accountExists(String email){
        boolean accountExists = false;
        Account tmp = null;
        try{
            tmp = accountRepository.getAccountByEmail(email);
            if(tmp != null){
                accountExists = true;
            }
        }catch(Exception e){
            logger.error(e);
        }
        return accountExists;
    }

    // Create account & saves it to database, this method only creates a user account
    public String createAccount(AccountRegisterRequest request){
        String registerStatus = null;
        boolean accountExists = accountExists(request.getEmail());

        // Only proceed if account is not already registered
        if(!accountExists){
            try{
                UUID uuid = UUID.randomUUID();
                String hashedPassword = bCryptPasswordEncoder.encode(request.getPassword());
                String hashedSQA = bCryptPasswordEncoder.encode(request.getSecret_question_answer());

                Account newUser = new Account(request.getUsername(), request.getEmail(), request.getFirstname(),
                        request.getLastname(), hashedPassword, request.getSecret_question(),
                        hashedSQA, uuid.toString(), AccountRole.USER);

                accountRepository.save(newUser);

                registerStatus = "Signup Successful";
            }catch(Exception e){
                logger.error("Failed to register" + e);
                registerStatus = "Signup failed";
            }
        }
        return registerStatus;
    }

    // TODO: update account details
    public String updateAccount(){
        return "TODO";
    }

    // TODO: Forgot Password
    public String forgotPassword(){
        return "TODO";
    }

    // TODO: Change Password
    public String changePassword(){
        return "TODO";
    }

}
