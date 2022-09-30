package rmit.sec.webstorepmicroservice.SessionKeyService.services;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rmit.sec.webstorepmicroservice.SessionKeyService.repository.SessionKeyRepository;
import rmit.sec.webstorepmicroservice.utils.EncryptionUtil;

@Service
@AllArgsConstructor
public class SessionKeyService {
//    private SecureRandom secureRandom;
    @Autowired
    private EncryptionUtil encryptionUtil;

    @Autowired
    private SessionKeyRepository sessionKeyRepository;

    // Temporary key for testing purposes
    private final String tmpkey = "u/Gu5posvwDsXUnV5Zaq4g==";

    // Decrypt a message encrypted with the server's RSA public key
    public String aesDecryptMessage(Long sessionID, String encryptedMessage) {
        return encryptionUtil.serverAESDecrypt(tmpkey, encryptedMessage);
    }

    // TEST METHOD
    public String aesEncryptMessage(Long sessionID, String plainText){
        return encryptionUtil.serverAESEncrypt(tmpkey, plainText);
    }

    public String rsaEncryptMessage(String plainText){
        return encryptionUtil.serverRSAEncrypt(plainText);
    }

    public String rsaDecryptMessage(String plainText){
        return encryptionUtil.serverRSADecrypt(plainText);
    }



}
