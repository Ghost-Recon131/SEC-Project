import CryptoJS from "crypto-js";

// Encrypt data
export function clientAESEncrypt(plainText){
    // Calculate IV based off secret key then convert the secret key to base 64
    var secretKey = localStorage.getItem('secretKey')
    var iv = CryptoJS.MD5(secretKey)
    secretKey = CryptoJS.enc.Base64.parse(secretKey)

    // Perform encryption
    var encrypted = CryptoJS.AES.encrypt(plainText, secretKey,
        {
            mode: CryptoJS.mode.CBC,
            padding: CryptoJS.pad.Pkcs7,
            iv: iv
        });
    // Return encrypted data encoded to base64
    return encrypted.ciphertext.toString(CryptoJS.enc.Base64);
}

// Decrypt data
export function clientAESDecrypt(cipherText){
    // Calculate IV based off secret key then convert the secret key to base 64
    var secretKey = localStorage.getItem('secretKey')
    var iv = CryptoJS.MD5(secretKey)
    secretKey = CryptoJS.enc.Base64.parse(secretKey)

    // Decrypt the data
    var decrypted = CryptoJS.AES.decrypt(cipherText, secretKey,
        {
            mode: CryptoJS.mode.CBC,
            padding: CryptoJS.pad.Pkcs7,
            iv: iv
        });

    // Encode decrypted data to utf8 string
    decrypted = decrypted.toString(CryptoJS.enc.Utf8);
    return decrypted
}
