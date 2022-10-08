import axios from 'axios'
import JSEncrypt from 'jsencrypt'

async function keyExchange(){
    let sessionID = null
    let sessionSecret = null

    // Get RSA public key from server
    const publicKey = (await axios.get(`http://localhost:8080/api/sessionKeyService/getServerPublicKey`)).data;

    // Generate random secret
    const secretKey = secretKeyGenerator()
    sessionStorage.setItem('secretKey', secretKey)

    // Perform RSA encryption
    const rsa = new JSEncrypt();
    rsa.setPublicKey(publicKey);
    sessionSecret = rsa.encrypt(secretKey)

    // Make POST request to backend
    sessionID = (await axios.post(`http://localhost:8080/api/sessionKeyService/keyExchange`, {
        sessionID: 0, // this is a dummy sessionID value as backend ignores it
        encryptedData: sessionSecret
    })).data


    // Check response from backend, if we have a JWT token, then save it, otherwise throw an error
    if (sessionID){
        sessionStorage.setItem('sessionID', sessionID)
    }else{
        console.error("key exchange failed")
    }

}


// Generates a random secret, default length of 32 characters
const secretKeyGenerator = (length = 32) => {
    // Characters that will be randomly drawn
    let chars = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';

    // Randomly pick characters and assemble it into a string
    let randomString = '';
    for (let i = 0; i < length; i++) {
        randomString += chars.charAt(Math.floor(Math.random() * chars.length));
    }

    return randomString;
};

export default keyExchange;