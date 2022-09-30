package rmit.sec.webstorepmicroservice.utils;

import lombok.AllArgsConstructor;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import static rmit.sec.webstorepmicroservice.security.SecurityConstant.RSA_PRIVATE;
import static rmit.sec.webstorepmicroservice.security.SecurityConstant.RSA_PUBLIC;


@Service
@AllArgsConstructor
public class EncryptionUtil {
    // RSA key pair for server


    private final Logger logger = LogManager.getLogger(this.getClass());

    // TEST METHOD: handle RSA encryption

    // This method will handle RSA Decryption
    public String serverRSAEncrypt(String plainText){
        String cipherText = null;
        try{
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, getPublicKey(RSA_PUBLIC));

            byte[] encryptedValue = cipher.doFinal(plainText.getBytes());

            cipherText = Base64.getEncoder().encodeToString(encryptedValue);
        }catch (Exception e){
            logger.error(e.getMessage());
        }
        return cipherText;
    }

    public String serverRSADecrypt(String cipherText){
        String plainText = null;
        try{
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");

            cipher.init(Cipher.DECRYPT_MODE, getPrivateKey(RSA_PRIVATE));

            byte[] decryptedValue = cipher.doFinal(Base64.getDecoder().decode(cipherText));

            plainText = new String(decryptedValue);
        }catch (Exception e){
            logger.error(e.getMessage());
        }
        return plainText;
    }

    // Supporting methods to get the public and private key for the server
    public PublicKey getPublicKey(String publicKeyString){
        PublicKey publicKey = null;
        try{
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(publicKeyString.getBytes()));
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            publicKey = keyFactory.generatePublic(keySpec);
        }catch (Exception e){
            logger.error(e.getMessage());
        }
        return publicKey;
    }

     private PrivateKey getPrivateKey(String privateKeyString){
        PrivateKey privateKey = null;
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKeyString.getBytes()));
        KeyFactory keyFactory = null;
        try {
            keyFactory = KeyFactory.getInstance("RSA");
            privateKey = keyFactory.generatePrivate(keySpec);

        }catch (Exception e){
            logger.error(e.getMessage());
        }
        return privateKey;
    }

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
