import { getGlobalState, setGlobalState } from "components/utils/globalState";
import cookie from "js-cookie";
import axios from "axios";
import { useEffect, useState } from "react";
import { useNavigate, Link } from "react-router-dom";
import {clientAESDecrypt} from "../security/EncryptionUtils";

export default function Component() {
    const navigate = useNavigate();
    const token = sessionStorage.getItem('jwt-token')
    const sessionID = sessionStorage.getItem('sessionID')
    var [user, setUser] = useState({});


    // As this page requires user to be logged in, we check if they have a valid login
    useEffect(() => {
        if (!token){
            navigate('/login')
            console.log("no valid login detected")
        }else{
            async function getUserInfo(){
                let currentUserDetails = await axios.get(getGlobalState("backendDomain") + "/api/authorised/viewAccountInfo?sessionID=" + sessionID,
                    {headers: {Authorization: token}});

                // Decrypt the data and set data to current user object
                currentUserDetails = currentUserDetails.data
                if(currentUserDetails){
                    user = {
                        "id": clientAESDecrypt(currentUserDetails.id),
                        "username": clientAESDecrypt(currentUserDetails.username),
                        "email": clientAESDecrypt(currentUserDetails.email),
                        "firstname": clientAESDecrypt(currentUserDetails.firstname),
                        "lastname": clientAESDecrypt(currentUserDetails.lastname),
                        "secretQuestion": clientAESDecrypt(currentUserDetails.secretQuestion),
                    }
                    setUser(user);
                }
            }
            try{
                getUserInfo()
            }catch (e) {
                console.log(e)
            }
        }
    }, []);

    return (
        <div>
            <h3 className="text-2xl font-bold text-white shadow-md rounded pt-2 pb-8 mb-4">Account Info</h3>
            <p>Account ID: {user.id}</p>
            <p>Username: {user.username}</p>
            <p>Email: {user.email}</p>
            <p>First Name: {user.firstname}</p>
            <p>Last Name: {user.lastname}</p>
            <p>Secret question: {user.secretQuestion}</p>
        </div>
    );
}
