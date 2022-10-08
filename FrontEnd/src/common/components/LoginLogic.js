import axios from 'axios'
import {clientAESDecrypt, clientAESEncrypt} from './EncryptionUtils'

async function loginLogic(username, password){
    const sessionID = sessionStorage.getItem('sessionID')

    // Calculate the ciphertext
    const encryptedUsername = clientAESEncrypt(username);
    const encryptedPassword = clientAESEncrypt(password);

    // Make POST request to backend
    try{
        const response = await axios.post(`http://localhost:8080/api/RegisterLogin/login?sessionID=`+ sessionID,
            {
                username: encryptedUsername,
                password: encryptedPassword
            }
        );
        var token = response.data.token
        // Check response from backend, if we have a JWT token, then save it, otherwise throw an error
        if (token){
            sessionStorage.setItem('jwt-token', token)
            console.log("jwt-token: " + sessionStorage.getItem('jwt-token'))
        }else{
            console.error("failed to login")
        }
    }catch (e) {
        console.log("Session ID: " + e)
        console.log("Error:  \n" + e)
    }
}

export default loginLogic