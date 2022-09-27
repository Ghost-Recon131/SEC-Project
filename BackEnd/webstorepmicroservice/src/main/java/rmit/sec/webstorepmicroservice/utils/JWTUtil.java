package rmit.sec.webstorepmicroservice.utils;

import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rmit.sec.webstorepmicroservice.Account.model.Account;
import rmit.sec.webstorepmicroservice.Account.repository.AccountRepository;
import rmit.sec.webstorepmicroservice.security.JwtTokenProvider;

import javax.servlet.http.HttpServletRequest;

import static rmit.sec.webstorepmicroservice.security.SecurityConstant.HEADER_STRING;
import static rmit.sec.webstorepmicroservice.security.SecurityConstant.TOKEN_PREFIX;

@Service
@AllArgsConstructor
public class JWTUtil {
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private AccountRepository accountRepository;
    private final Logger logger = LogManager.getLogger(this.getClass());

    public Account getUserIdByJWT(HttpServletRequest request){
        String authHeader = request.getHeader(HEADER_STRING);

        Long userId = null;
        Account userAccount = null;

        if(authHeader != null && authHeader.startsWith(TOKEN_PREFIX)){
            String jwt = authHeader.substring(7);
            userId = jwtTokenProvider.getUserIdFromJWT(jwt);
        }
        try{
            assert userId != null;
            userAccount = accountRepository.getById(userId);
        }catch (Exception e){
            logger.error(e);
        }
        return userAccount;
    }

}
