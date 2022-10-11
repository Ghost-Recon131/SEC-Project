import { getGlobalState, setGlobalState } from "components/utils/globalState";
import axios from "axios";
import { useEffect, useState } from "react";
import { useNavigate, Link } from "react-router-dom";
import {clientAESDecrypt} from "../security/EncryptionUtils";
import {getUserDetails} from "../utils/getUserDetails";

export default function Component() {
    const navigate = useNavigate();
    const token = localStorage.getItem('jwt-token')
    const sessionID = localStorage.getItem('sessionID')
    var [user, setUser] = useState({});


    // As this page requires user to be logged in, we check if they have a valid login
    useEffect(() => {
        if (!token){
            navigate('/login')
            console.log("no valid login detected")
        }else{
            async function getUserInfo(){
                const currentUserDetails = JSON.parse(await getUserDetails());
                if(currentUserDetails){
                    user = {
                        "id": currentUserDetails.id,
                        "username": currentUserDetails.username,
                        "email": currentUserDetails.email,
                        "firstname": currentUserDetails.firstname,
                        "lastname": currentUserDetails.lastname,
                        "secretQuestion": currentUserDetails.secretQuestion,
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
            <h1 className="text-2xl mb-8">Account Info</h1>
            <p className="mb-4">Account ID: {user.id}</p>
            <p className="mb-4">Username: {user.username}</p>
            <p className="mb-4">Email: {user.email}</p>
            <p className="mb-4">First Name: {user.firstname}</p>
            <p className="mb-4">Last Name: {user.lastname}</p>
            <p className="mb-4">Secret question: {user.secretQuestion}</p>
            <div>
            <button className="mb-4"
                className="text-yellow-400 mb-4"
                onClick={() => navigate("/modifyaccountinfo")}>
                Edit Account Details
            </button>
            </div>
        </div>
    );
}
