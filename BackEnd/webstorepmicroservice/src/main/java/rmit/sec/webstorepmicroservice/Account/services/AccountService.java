package rmit.sec.webstorepmicroservice.Account.services;

import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rmit.sec.webstorepmicroservice.Account.model.Account;
import rmit.sec.webstorepmicroservice.Account.repository.AccountRepository;
import rmit.sec.webstorepmicroservice.Account.requests.AccountRegisterRequest;
import rmit.sec.webstorepmicroservice.Account.requests.ForgotPasswordRequest;
import rmit.sec.webstorepmicroservice.utils.AccountRole;

import java.util.UUID;

@Service
@AllArgsConstructor
public class AccountService implements UserDetailsService {
    @Autowired
    private AccountRepository accountRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

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

    // Determine if a given username is already associated with an account
    public boolean usernameExists(String username){
        boolean accountExists = false;
        Account tmp = null;
        try{
            tmp = accountRepository.getAccountByUsername(username);
            if(tmp != null){
                accountExists = true;
            }
        }catch(Exception e){
            logger.error(e);
        }
        return accountExists;
    }

    // Determine if a given email is already associated with an account
    public boolean emailExists(String email){
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
        boolean usernameTaken = usernameExists(request.getUsername());
        boolean emailTaken = emailExists(request.getEmail());

        // Only proceed if account is not already registered
        if(usernameTaken){
            registerStatus = "Your username has been taken, please choose a different username";
        }else if(emailTaken){
            registerStatus = "Your email already exists, did you forget your password?";
        }else{
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

    // Allow user to reset forgotten Password
    public String forgotPassword(ForgotPasswordRequest request){
        Account account = null;
        String result = "";

        if (usernameExists(request.getUsername())){
            try{
                account = accountRepository.getAccountByUsername(request.getUsername());

                boolean secretQuestionMatch = account.getSecretQuestion().equals(request.getSecret_question());
                String hashedSecretQuestionAnswer = bCryptPasswordEncoder.encode(request.getSecret_question_answer());
                boolean getSecretQuestionAnswerMatch = account.getSecretQuestionAnswer().equals(hashedSecretQuestionAnswer);

                if(secretQuestionMatch && getSecretQuestionAnswerMatch){
                    String newPassword = bCryptPasswordEncoder.encode(request.getNewPassword());
                    account.setPassword(newPassword);
                    accountRepository.save(account);
                    result = "Password successfully changed";
                }else{
                    result = "The provided secret question and or answer to secret question is invalid.";
                }
            }catch (Exception e){
                logger.error(e.getMessage());
                result = "Exception occurred, password was not changed";
            }
        }else{
            result = "The provided username does not exist";
        }
        return result;
    }


}
