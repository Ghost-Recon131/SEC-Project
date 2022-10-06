package rmit.sec.webstorepmicroservice.Account.controller;

import lombok.AllArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rmit.sec.webstorepmicroservice.Account.model.Account;
import rmit.sec.webstorepmicroservice.Account.requests.ChangePasswordRequest;
import rmit.sec.webstorepmicroservice.Account.requests.UpdateAccountInfoRequest;
import rmit.sec.webstorepmicroservice.Account.services.AccountServiceAuthorised;
import rmit.sec.webstorepmicroservice.SessionKeyService.services.SessionKeyService;
import rmit.sec.webstorepmicroservice.utils.JWTUtil;

import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/authorised")
@AllArgsConstructor
public class AccountControllerPrivate {
    @Autowired
    private AccountServiceAuthorised accountServiceAuthorised;
    @Autowired
    private JWTUtil jwtUtil;
    @Autowired
    private SessionKeyService sessionKeyService;

    // Endpoint to view account's information, requires valid JWT token
    @GetMapping(path = "/viewAccountInfo")
    public JSONObject getAccountInfo(HttpServletRequest request, @RequestParam Long sessionID) {
        // Get the user account details
        Account userAccount = accountServiceAuthorised.getAccountInfo(request);

        // Pick details to return to frontend & then encrypt the output
        JSONObject encryptedResponse = new JSONObject();
        encryptedResponse.put("id", sessionKeyService.aesEncryptMessage(sessionID, userAccount.getId().toString()));
        encryptedResponse.put("username", sessionKeyService.aesEncryptMessage(sessionID, userAccount.getUsername()));
        encryptedResponse.put("email", sessionKeyService.aesEncryptMessage(sessionID, userAccount.getEmail()));
        encryptedResponse.put("firstname", sessionKeyService.aesEncryptMessage(sessionID, userAccount.getFirstname()));
        encryptedResponse.put("lastname", sessionKeyService.aesEncryptMessage(sessionID, userAccount.getLastname()));
        encryptedResponse.put("secretQuestion", sessionKeyService.aesEncryptMessage(sessionID, userAccount.getSecretQuestion()));

        return encryptedResponse;
    }

    // Endpoint to change user's own password, requires valid JWT token
    @PostMapping(path = "/changepassword")
    public String changePassword(HttpServletRequest request, @RequestParam Long sessionID, @RequestBody ChangePasswordRequest changePasswordRequest) {
        Long userID = jwtUtil.getUserIdByJWT(request).getId();

        String response = accountServiceAuthorised.changePassword(userID, changePasswordRequest);
        return sessionKeyService.aesEncryptMessage(sessionID, response);
    }

    // Endpoint to update user's account information, requires valid JWT token
    @PutMapping(path = "/updateAccountInfo")
    public String updateAccountInfo(HttpServletRequest request, @RequestParam Long sessionID, @RequestBody UpdateAccountInfoRequest updateInfoRequest) {
        Long userID = jwtUtil.getUserIdByJWT(request).getId();
        String response = accountServiceAuthorised.updateAccount(userID, updateInfoRequest);
        return sessionKeyService.aesEncryptMessage(sessionID, response);
    }

}
