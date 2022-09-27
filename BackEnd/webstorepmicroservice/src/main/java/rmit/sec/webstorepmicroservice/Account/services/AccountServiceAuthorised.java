package rmit.sec.webstorepmicroservice.Account.services;

import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import rmit.sec.webstorepmicroservice.Account.model.Account;
import rmit.sec.webstorepmicroservice.Account.repository.AccountRepository;
import rmit.sec.webstorepmicroservice.Account.requests.ChangePasswordRequest;
import rmit.sec.webstorepmicroservice.Account.requests.UpdateAccountInfoRequest;
import rmit.sec.webstorepmicroservice.utils.JWTUtil;

import javax.servlet.http.HttpServletRequest;

@Service
@AllArgsConstructor
public class AccountServiceAuthorised {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private JWTUtil jwtUtil;
    private final Logger logger = LogManager.getLogger(this.getClass());

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    // Return a user's account information
    public Account getAccountInfo(HttpServletRequest request){
        return jwtUtil.getUserIdByJWT(request);
    }

    // Update account details
    public String updateAccount(Long userID, UpdateAccountInfoRequest updateInfoRequest){
        String result = "";
        try{
            Account toUpdate = accountRepository.getById(userID);

            if(updateInfoRequest.getEmail() != null && !updateInfoRequest.getEmail().isEmpty()){
                toUpdate.setEmail(updateInfoRequest.getEmail());
            }

            if(updateInfoRequest.getFirstname() != null && !updateInfoRequest.getFirstname().isEmpty()){
                toUpdate.setFirstname(updateInfoRequest.getFirstname());
            }

            if(updateInfoRequest.getLastname() != null && !updateInfoRequest.getLastname().isEmpty()){
                toUpdate.setLastname(updateInfoRequest.getLastname());
            }

            accountRepository.save(toUpdate);
            result = "Account details updated successfully!";
        }catch (Exception e){
            result = "Exception occurred, your password is not changed";
            logger.error(e.getMessage());
        }
        return result;
    }

    // Change Password
    public String changePassword(Long userID, ChangePasswordRequest changePasswordRequest){
        String result = "";
        try{
            String newHashedPassword = bCryptPasswordEncoder.encode(changePasswordRequest.getPassword());
            Account toUpdate = accountRepository.getById(userID);
            toUpdate.setPassword(newHashedPassword);
            accountRepository.save(toUpdate);
            result = "Password updated successfully!";
        }catch (Exception e){
            result = "Exception occurred, your password is not changed";
            logger.error(e.getMessage());
        }
        return result;
    }

}
