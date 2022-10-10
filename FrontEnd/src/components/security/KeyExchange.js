import axios from 'axios'
import JSEncrypt from 'jsencrypt'
import {getGlobalState} from "../utils/globalState";

async function keyExchange(){
    let sessionID = null
    let sessionSecret = null

    // Get RSA public key from server
    const publicKey = (await axios.get(getGlobalState("backendDomain") + `/api/sessionKeyService/getServerPublicKey`)).data;

    // Generate random secret
    const secretKey = secretKeyGenerator()
    localStorage.setItem('secretKey', secretKey)

    // Perform RSA encryption
    const rsa = new JSEncrypt();
    rsa.setPublicKey(publicKey);
    sessionSecret = rsa.encrypt(secretKey)

    // Construct data to finish key exchange
    const data =  {
        sessionID: 0, // this is a dummy sessionID value as backend ignores it
        encryptedData: sessionSecret
    }
    // Make POST request to backend
    sessionID = (await axios.post(getGlobalState("backendDomain") + `/api/sessionKeyService/keyExchange`, data)).data;
    // Check response from backend, if we have a JWT token, then save it, otherwise throw an error
    if (sessionID){
        localStorage.setItem('sessionID', sessionID)
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