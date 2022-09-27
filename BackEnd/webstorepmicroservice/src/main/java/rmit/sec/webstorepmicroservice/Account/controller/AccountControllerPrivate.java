package rmit.sec.webstorepmicroservice.Account.controller;

import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rmit.sec.webstorepmicroservice.Account.model.Account;
import rmit.sec.webstorepmicroservice.Account.requests.AccountRegisterRequest;
import rmit.sec.webstorepmicroservice.Account.services.AccountService;
import rmit.sec.webstorepmicroservice.Account.services.AccountServiceAuthorised;

import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/authorised")
@AllArgsConstructor
public class AccountControllerPrivate {
    @Autowired
    private AccountServiceAuthorised accountServiceAuthorised;

    private final Logger logger = LogManager.getLogger(this.getClass());

    @GetMapping(path = "/viewAccountInfo")
    public Account getAccountInfo(HttpServletRequest request) {
        return accountServiceAuthorised.getAccountInfo(request);
    }

}
