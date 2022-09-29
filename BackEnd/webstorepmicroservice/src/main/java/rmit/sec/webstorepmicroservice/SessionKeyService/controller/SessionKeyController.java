package rmit.sec.webstorepmicroservice.SessionKeyService.controller;

import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rmit.sec.webstorepmicroservice.SessionKeyService.requests.EncryptedDataRequest;
import rmit.sec.webstorepmicroservice.SessionKeyService.services.SessionKeyService;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/DHKE")
@AllArgsConstructor
public class SessionKeyController {
    // TODO
    @Autowired
    private SessionKeyService sessionKeyService;

    private final Logger logger = LogManager.getLogger(this.getClass());

    // This endpoint is used to test decrypting message sent with encrypted data
    @GetMapping(path = "/decryptDSAKey")
    public String decryptDSAKey(@RequestBody EncryptedDataRequest request){
        return sessionKeyService.decryptMessage(request.getSessionID(), request.getEncryptedData());
    }

    // This endpoint is used to test encrypting message sent with encrypted data
    @PostMapping(path = "/encryptMessage")
    public String serverRSAEncrypt(@RequestBody EncryptedDataRequest request){
        return sessionKeyService.encryptMessage(request.getSessionID(), request.getEncryptedData());
    }

}
