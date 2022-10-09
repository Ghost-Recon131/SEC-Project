import axios from 'axios'
import {clientAESEncrypt} from './EncryptionUtils'

async function loginLogic(username, email, firstname, lastname, password, secret_question, secret_answer){
    const sessionID = sessionStorage.getItem('sessionID')
    let response = null
    let token = null

    // Calculate the ciphertext
    const encryptedUsername = clientAESEncrypt(username);
    const encryptedPassword = clientAESEncrypt(password);

    // Make POST request to backend
    try{
        response = await axios.post(`http://localhost:8080/api/RegisterLogin/login?sessionID=`+ sessionID,
            {
                username: encryptedUsername,
                password: encryptedPassword
            }
        );
        token = response.data.token;

        // Check response from backend, if we have a JWT token, then save it, otherwise throw an error
        if(token.startsWith("Bearer ")){
            sessionStorage.setItem('jwt-token', token)
            // console.log("jwt-token: " + sessionStorage.getItem('jwt-token'))
        }
    }catch (e) {
        token = "Invalid credentials"
        console.error("failed to login")
    }
    return token
}

export default loginLogic