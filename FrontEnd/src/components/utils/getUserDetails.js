import axios from "axios";
import {getGlobalState} from "./globalState";
import {clientAESDecrypt} from "../security/EncryptionUtils";

// Utility function to get user details from the server
export async function getUserDetails() {
    const sessionID = localStorage.getItem("sessionID");
    const token = localStorage.getItem("jwt-token");

    // Get data from backend
    const getUserDetails = await axios.get(getGlobalState("backendDomain") + "/api/authorised/viewAccountInfo?sessionID=" + sessionID,
        { headers: { Authorization: token } });
    let encryptedUserDetails = getUserDetails.data;

    // Decrypt data
    const user = {
        "id": clientAESDecrypt(encryptedUserDetails.id),
        "username": clientAESDecrypt(encryptedUserDetails.username),
        "email": clientAESDecrypt(encryptedUserDetails.email),
        "firstname": clientAESDecrypt(encryptedUserDetails.firstname),
        "lastname": clientAESDecrypt(encryptedUserDetails.lastname),
        "secretQuestion": clientAESDecrypt(encryptedUserDetails.secretQuestion),
    }
    // Need to return a string otherwise React is unable to get the object
    return JSON.stringify(user);
}

