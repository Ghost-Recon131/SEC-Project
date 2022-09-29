package rmit.sec.webstorepmicroservice.utils;

import lombok.AllArgsConstructor;
import java.util.Base64;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;


@Service
@AllArgsConstructor
public class EncryptionUtil {

    private final Logger logger = LogManager.getLogger(this.getClass());

    // Run the provided JS code inside our java program
//    public String serverRSADecrypt(String encryptedMessage) {
//        ScriptEngine jsRuntime = null;
//        String decryptedMessage = null;
//        try {
//            jsRuntime = new ScriptEngineManager().getEngineByName("src/main/resources/encryptionLinker.js");
//            Invocable jsFunction = (Invocable) jsRuntime;
//
//            //function RSA_encryption(message, publicKey)
//            decryptedMessage = jsFunction.invokeFunction("RSA_decryption", encryptedMessage, RSA_PUBLIC).toString();
//
//            System.err.println("Result: " + decryptedMessage);
//        } catch (Exception e) {
//            logger.error(e.getMessage());
//        }
//        return decryptedMessage;
//    }

    // This method will encrypt data with using an agreed upon session key
    public String serverAESEncrypt(String sessionKey, String plainText){
        String encryptedMessage = null;
        try {
            SecretKey key = new SecretKeySpec(Base64.getDecoder().decode(sessionKey), "AES");

            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, key);

            byte[] encryptedValue = cipher.doFinal(plainText.getBytes());

            encryptedMessage = Base64.getEncoder().encodeToString(encryptedValue);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return encryptedMessage;
    }

    // This method will decrypt the encrypted data
    public String serverAESDecrypt(String sessionKey, String cipherText) {
        String decryptedMessage = null;
        try {
            SecretKey key = new SecretKeySpec(Base64.getDecoder().decode(sessionKey), "AES");

            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, key);

            byte[] decodeBase64 = Base64.getDecoder().decode(cipherText);
            byte[] decryptedVal = cipher.doFinal(decodeBase64);

            decryptedMessage = new String(decryptedVal);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return decryptedMessage;
    }


}
