import axios from 'axios'
import {clientAESEncrypt} from './EncryptionUtils'

async function registerLogic(username, email, firstname, lastname, password, secret_question, secret_question_answer){
    const sessionID = sessionStorage.getItem('sessionID')
    let response = null;

    // Only continue if values are not null
    if(username !== "" && email !== "" && firstname !== "" && lastname !== "" && password !== "" && secret_question !== "" && secret_question_answer !== ""){
        try{
            response = await axios.post(`http://localhost:8080/api/RegisterLogin/register?sessionID=` + sessionID, {
                "username": clientAESEncrypt(username),
                "email": clientAESEncrypt(email),
                "firstname": clientAESEncrypt(firstname),
                "lastname": clientAESEncrypt(lastname),
                "password": clientAESEncrypt(password),
                "secret_question": clientAESEncrypt(secret_question),
                "secret_question_answer": clientAESEncrypt(secret_question_answer)
            });
            response = response.data.toString()
        }catch (e) {
            console.log("Error:  \n" + e)
        }
    }
}

export default registerLogic