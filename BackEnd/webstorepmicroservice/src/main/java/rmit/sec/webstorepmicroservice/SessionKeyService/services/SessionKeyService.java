package rmit.sec.webstorepmicroservice.SessionKeyService.services;

import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rmit.sec.webstorepmicroservice.SessionKeyService.model.SessionKey;
import rmit.sec.webstorepmicroservice.SessionKeyService.repository.SessionKeyRepository;
import rmit.sec.webstorepmicroservice.utils.EncryptionUtil;

import java.util.Random;

@Service
@AllArgsConstructor
public class SessionKeyService {
//    private SecureRandom secureRandom;
    @Autowired
    private EncryptionUtil encryptionUtil;
    @Autowired
    private SessionKeyRepository sessionKeyRepository;
    private final Logger logger = LogManager.getLogger(this.getClass());

    // Temporary key for testing purposes
//    private final String tmpkey = "u/Gu5posvwDsXUnV5Zaq4g==";
    private final String tmpkey = "PreachyImposing2";

    // Handles the key exchange process
    public Long keyExchange(String encryptedKey){
        Long sessionID = null;
        String decryptedKey = null;
        Integer tmpSessionID = new Random().nextInt(10000);

        // Decrypt the key encrypted with the server's public key
        decryptedKey = encryptionUtil.serverRSADecrypt(encryptedKey);

        // Store new session in database if decryption is successful
        if(decryptedKey != null && !decryptedKey.isEmpty()){
            String securedSessionKey = encryptionUtil.serverRSAEncrypt(decryptedKey);

            SessionKey sessionKey = new SessionKey(securedSessionKey, tmpSessionID);
            sessionKeyRepository.save(sessionKey);

            // Retrieve the sessionID from DB and return it to client
            sessionID = sessionKeyRepository.getSessionKeyByTmpSessionID(tmpSessionID).getSessionID();

            // Remove the tmpID
            SessionKey removeTmpID = sessionKeyRepository.getBySessionID(sessionID);
            removeTmpID.setTmpSessionID(null);
            sessionKeyRepository.save(sessionKey);
        }else{
            logger.debug("Failed to establish session key, error in RSA decryption process");
        }
        return sessionID;
    }

    // Decrypt a message encrypted with the server's RSA public key
    public String aesDecryptMessage(Long sessionID, String encryptedMessage) {
        String aesSessionKey = null;
        try{
            SessionKey sessionKeyObject = sessionKeyRepository.getBySessionID(sessionID);
            aesSessionKey = encryptionUtil.serverRSADecrypt(sessionKeyObject.getSessionKey());
        }catch (Exception e){
            logger.error(e.getMessage());
            logger.warn("Possible failed to retrieve encryption key from DB");
        }
        return encryptionUtil.serverAESDecrypt(aesSessionKey, encryptedMessage);
    }

    // TEST METHOD
    public String aesEncryptMessage(Long sessionID, String plainText){
        String aesSessionKey = null;
        try{
            SessionKey sessionKeyObject = sessionKeyRepository.getBySessionID(sessionID);
            aesSessionKey = encryptionUtil.serverRSADecrypt(sessionKeyObject.getSessionKey());
        }catch (Exception e){
            logger.error(e.getMessage());
            logger.warn("Possible failed to retrieve encryption key from DB");
        }
        return encryptionUtil.serverAESEncrypt(aesSessionKey, plainText);
    }

    // Performs RSA encryption using the server's public key
    public String rsaEncryptMessage(String plainText){
        return encryptionUtil.serverRSAEncrypt(plainText);
    }

    // Performs RSA encryption using the server's private key
    public String rsaDecryptMessage(String plainText){
        return encryptionUtil.serverRSADecrypt(plainText);
    }



}
