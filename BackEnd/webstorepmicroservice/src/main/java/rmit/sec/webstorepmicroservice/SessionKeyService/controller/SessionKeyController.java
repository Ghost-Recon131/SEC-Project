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
@RequestMapping("/api/sessionKeyService")
@AllArgsConstructor
public class SessionKeyController {
    // TODO
    @Autowired
    private SessionKeyService sessionKeyService;

    private final Logger logger = LogManager.getLogger(this.getClass());

    // This endpoint is used to test decrypting message sent with encrypted data
    @GetMapping(path = "/aesDecrypt")
    public String serverAESDecrypt(@RequestBody EncryptedDataRequest request){
        return sessionKeyService.aesDecryptMessage(request.getSessionID(), request.getEncryptedData());
    }

    // This endpoint is used to test encrypting message sent with encrypted data
    @PostMapping(path = "/aesEncrypt")
    public String serverAESEncrypt(@RequestBody EncryptedDataRequest request){
        return sessionKeyService.aesEncryptMessage(request.getSessionID(), request.getEncryptedData());
    }

    @PostMapping(path = "/rsaEncrypt")
    public String serverRSAEncrypt(@RequestBody EncryptedDataRequest request){
        return sessionKeyService.rsaEncryptMessage(request.getEncryptedData());
    }

    @GetMapping(path = "/rsaDecrypt")
    public String serverRSADecrypt(@RequestBody EncryptedDataRequest request){
        return sessionKeyService.rsaDecryptMessage(request.getEncryptedData());
    }

}
