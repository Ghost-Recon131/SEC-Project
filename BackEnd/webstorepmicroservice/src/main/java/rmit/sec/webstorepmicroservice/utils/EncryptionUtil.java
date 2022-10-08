package rmit.sec.webstorepmicroservice.utils;

import lombok.AllArgsConstructor;

import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import static rmit.sec.webstorepmicroservice.security.SecurityConstant.RSA_PRIVATE;
import static rmit.sec.webstorepmicroservice.security.SecurityConstant.RSA_PUBLIC;


@Service
@AllArgsConstructor
public class EncryptionUtil {
    private final Logger logger = LogManager.getLogger(this.getClass());

    // Handle RSA encryption
    public String serverRSAEncrypt(String plainText){
        String cipherText = null;
        try{
            // Select RSA algorithm then get the key & cipher
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, getPublicKey(RSA_PUBLIC));

            // Perform the encryption and encode ciphertext to base64, then return as normal string
            byte[] encryptedValue = cipher.doFinal(plainText.getBytes());
            cipherText = Base64.getEncoder().encodeToString(encryptedValue);
        }catch (Exception e){
            logger.error(e.getMessage());
        }
        return cipherText;
    }

    // Handle RSA Decryption
    public String serverRSADecrypt(String cipherText){
        String plainText = null;
        try{
            // Select RSA algorithm then get the key & cipher
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.DECRYPT_MODE, getPrivateKey(RSA_PRIVATE));

            // Perform the decryption and decode to base64 and return plaintext as normal string
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

    // Calculates the actual RSA private key using the provided secret
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
            // Calculate the IV based off the session key
            MessageDigest MD5 = MessageDigest.getInstance("MD5");
            byte[] hashedIV = MD5.digest(sessionKey.getBytes(StandardCharsets.UTF_8));
            AlgorithmParameterSpec iv = new IvParameterSpec(hashedIV);

            // Generate the encryption key
            SecretKeySpec encryptionKey = new SecretKeySpec(Base64.getDecoder().decode(sessionKey), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, encryptionKey, iv);

            // Encrypt the plaintext then encode it to base64 and return as normal string
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
            // Calculate the IV based off the session key
            MessageDigest MD5 = MessageDigest.getInstance("MD5");
            byte[] hashedIV = MD5.digest(sessionKey.getBytes(StandardCharsets.UTF_8));
            AlgorithmParameterSpec iv = new IvParameterSpec(hashedIV);

            // Generate the decryption key used for AES
            SecretKeySpec decryptionKey = new SecretKeySpec(Base64.getDecoder().decode(sessionKey), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, decryptionKey, iv);

            // Decrypt the cipher text and decode it to a normal string
            byte[] decodeBase64 = Base64.getDecoder().decode(cipherText);
            byte[] decryptedVal = cipher.doFinal(decodeBase64);
            decryptedMessage = new String(decryptedVal);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return decryptedMessage;
    }

}
