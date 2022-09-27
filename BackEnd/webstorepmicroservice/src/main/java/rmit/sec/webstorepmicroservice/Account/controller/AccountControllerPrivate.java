package rmit.sec.webstorepmicroservice.Account.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rmit.sec.webstorepmicroservice.Account.model.Account;
import rmit.sec.webstorepmicroservice.Account.requests.ChangePasswordRequest;
import rmit.sec.webstorepmicroservice.Account.requests.UpdateAccountInfoRequest;
import rmit.sec.webstorepmicroservice.Account.services.AccountServiceAuthorised;
import rmit.sec.webstorepmicroservice.utils.JWTUtil;

import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/authorised")
@AllArgsConstructor
public class AccountControllerPrivate {
    @Autowired
    private AccountServiceAuthorised accountServiceAuthorised;
    @Autowired
    private JWTUtil jwtUtil;

    // Endpoint to view account's information, requires valid JWT token
    @GetMapping(path = "/viewAccountInfo")
    public Account getAccountInfo(HttpServletRequest request) {
        return accountServiceAuthorised.getAccountInfo(request);
    }

    // Endpoint to change user's own password, requires valid JWT token
    @PostMapping(path = "/changepassword")
    public String changePassword(HttpServletRequest request, @RequestBody ChangePasswordRequest changePasswordRequest) {
        Long userID = jwtUtil.getUserIdByJWT(request).getId();
        return accountServiceAuthorised.changePassword(userID, changePasswordRequest);
    }

    // Endpoint to update user's account information, requires valid JWT token
    @PutMapping(path = "/updateAccountInfo")
    public String updateAccountInfo(HttpServletRequest request, @RequestBody UpdateAccountInfoRequest updateInfoRequest) {
        Long userID = jwtUtil.getUserIdByJWT(request).getId();
        return accountServiceAuthorised.updateAccount(userID, updateInfoRequest);
    }

}
