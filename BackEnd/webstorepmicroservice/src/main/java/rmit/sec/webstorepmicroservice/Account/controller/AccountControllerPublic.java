package rmit.sec.webstorepmicroservice.Account.controller;

import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import rmit.sec.webstorepmicroservice.Account.requests.AccountRegisterRequest;
import rmit.sec.webstorepmicroservice.Account.requests.LoginRequest;
import rmit.sec.webstorepmicroservice.Account.services.AccountService;
import rmit.sec.webstorepmicroservice.security.JWTLoginSucessReponse;
import rmit.sec.webstorepmicroservice.security.JwtTokenProvider;

import javax.persistence.Access;

import static rmit.sec.webstorepmicroservice.security.SecurityConstant.TOKEN_PREFIX;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/RegisterLogin")
@AllArgsConstructor
public class AccountControllerPublic {
    @Autowired
    private AccountService accountService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    private final Logger logger = LogManager.getLogger(this.getClass());

    // Endpoint for registering an account
    @PostMapping(path = "/register")
    public String registerAccount(@RequestBody AccountRegisterRequest registerAccountRequest) {
        return accountService.createAccount(registerAccountRequest);
    }

    // Endpoint for login. Validate login credentials then return a JWT token if successful
    @PostMapping(path = "/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest){
        System.err.println("Received username: " + loginRequest.getUsername());
        System.err.println("Received password: " + loginRequest.getPassword());

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = TOKEN_PREFIX +  jwtTokenProvider.generateToken(authentication);

        return ResponseEntity.ok(new JWTLoginSucessReponse(true, jwt));
    }

}
