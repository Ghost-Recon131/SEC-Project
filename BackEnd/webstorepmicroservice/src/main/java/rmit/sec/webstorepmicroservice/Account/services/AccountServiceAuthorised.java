package rmit.sec.webstorepmicroservice.Account.services;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rmit.sec.webstorepmicroservice.Account.model.Account;
import rmit.sec.webstorepmicroservice.Account.repository.AccountRepository;
import rmit.sec.webstorepmicroservice.utils.JWTUtil;

import javax.servlet.http.HttpServletRequest;

@Service
@NoArgsConstructor
public class AccountServiceAuthorised {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private JWTUtil jwtUtil;

    // Return a user's account information
    public Account getAccountInfo(HttpServletRequest request){
        return jwtUtil.getUserIdByJWT(request);
    }

}
