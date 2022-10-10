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
import rmit.sec.webstorepmicroservice.Account.requests.ForgotPasswordRequest;
import rmit.sec.webstorepmicroservice.Account.requests.LoginRequest;
import rmit.sec.webstorepmicroservice.Account.services.AccountService;
import rmit.sec.webstorepmicroservice.SessionKeyService.services.SessionKeyService;
import rmit.sec.webstorepmicroservice.security.JWTLoginSucessReponse;
import rmit.sec.webstorepmicroservice.security.JwtTokenProvider;
import rmit.sec.webstorepmicroservice.utils.EncryptionUtil;

import static rmit.sec.webstorepmicroservice.security.SecurityConstant.TOKEN_PREFIX;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/RegisterLogin")
@AllArgsConstructor
public class AccountControllerPublic {
    @Autowired
    private AccountService accountService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private SessionKeyService sessionKeyService;
    @Autowired
    private EncryptionUtil encryptionUtil;

    private final Logger logger = LogManager.getLogger(this.getClass());

    // Endpoint for registering an account
    @PostMapping(path = "/register")
    public String registerAccount(@RequestParam Long sessionID, @RequestBody AccountRegisterRequest registerAccountRequest) {
        String sessionKey = sessionKeyService.getAESKey(sessionID);
        // Decrypt the data
        registerAccountRequest.setUsername(encryptionUtil.serverAESDecrypt(sessionKey, registerAccountRequest.getUsername()));
        registerAccountRequest.setEmail(encryptionUtil.serverAESDecrypt(sessionKey, registerAccountRequest.getEmail()));
        registerAccountRequest.setFirstname(encryptionUtil.serverAESDecrypt(sessionKey, registerAccountRequest.getFirstname()));
        registerAccountRequest.setLastname(encryptionUtil.serverAESDecrypt(sessionKey, registerAccountRequest.getLastname()));
        registerAccountRequest.setPassword(encryptionUtil.serverAESDecrypt(sessionKey, registerAccountRequest.getPassword()));
        registerAccountRequest.setSecret_question(encryptionUtil.serverAESDecrypt(sessionKey, registerAccountRequest.getSecret_question()));
        registerAccountRequest.setSecret_question_answer(encryptionUtil.serverAESDecrypt(sessionKey, registerAccountRequest.getSecret_question_answer()));

        // Attempt to register the account then return the result
        String result = accountService.createAccount(registerAccountRequest);
        return sessionKeyService.aesEncryptMessage(sessionID, result);
    }

    // Endpoint for login. Validate login credentials then return a JWT token if successful
    @PostMapping(path = "/login")
    public ResponseEntity<?> login(@RequestParam Long sessionID, @RequestBody LoginRequest loginRequest){
        String sessionKey = sessionKeyService.getAESKey(sessionID);
        String decryptedUsername = encryptionUtil.serverAESDecrypt(sessionKey, loginRequest.getUsername());
        String decryptedPassword = encryptionUtil.serverAESDecrypt(sessionKey, loginRequest.getPassword());

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        decryptedUsername,
                        decryptedPassword
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = TOKEN_PREFIX +  jwtTokenProvider.generateToken(authentication);

        return ResponseEntity.ok(new JWTLoginSucessReponse(true, jwt));
    }

    // Endpoint for resetting a forgotten password
    @PostMapping(path = "/forgotPassword")
    public String forgotPassword(@RequestParam Long sessionID, @RequestBody ForgotPasswordRequest request){
        String sessionKey = sessionKeyService.getAESKey(sessionID);
        // Decrypt the original request and then create a decrypted request to pass to accountService
        ForgotPasswordRequest decryptedRequest = new ForgotPasswordRequest(
                encryptionUtil.serverAESDecrypt(sessionKey, request.getUsername()),
                encryptionUtil.serverAESDecrypt(sessionKey, request.getSecret_question()),
                encryptionUtil.serverAESDecrypt(sessionKey, request.getSecret_question_answer()),
                encryptionUtil.serverAESDecrypt(sessionKey, request.getNewPassword())
        );

        return encryptionUtil.serverAESEncrypt(sessionKey, accountService.forgotPassword(decryptedRequest));
    }

}
