import { getGlobalState, setGlobalState } from "components/utils/globalState";
import axios from "axios";
import {useEffect, useState} from "react";
import {useNavigate,} from 'react-router-dom';
import {clientAESDecrypt, clientAESEncrypt} from "../security/EncryptionUtils";
import {getUserDetails} from "../utils/getUserDetails";


export default function Component() {
    // Setup variables for use later
    var [formData, setFormData] = useState({email: "", firstname: "", lastname: "" });
    var {email, firstname, lastname} = formData;
    var [error, setError] = useState("");
    const navigate = useNavigate();
    const token = localStorage.getItem('jwt-token')
    const sessionID = localStorage.getItem('sessionID')

    // As this page requires user to be logged in, we check if they have a valid login
    useEffect(() => {
        if (!token){
            navigate('/login')
            console.log("no valid login detected")
        }else{
            async function preFillCurrentUserInfo(){
                const currentUserDetails = JSON.parse(await getUserDetails());
                // Decrypt the data and prefill current values
                if(currentUserDetails){
                    const decrypted = {
                        "email": currentUserDetails.email,
                        "firstname": currentUserDetails.firstname,
                        "lastname": currentUserDetails.lastname
                    }
                    setFormData(decrypted);
                }
            }
            try{
                preFillCurrentUserInfo()
            }catch (e) {
                console.log(e)
            }
        }
    }, []);


    function formInputs(event) {
        event.preventDefault();
        var { name, value } = event.target;
        setFormData({ ...formData, [name]: value });
    }

    // Function to submit the updated data to backend
    async function submit(event) {
        event.preventDefault();
        try {
            // Construct the encrypted data object
            const data = {
                "email": clientAESEncrypt(email),
                "firstname": clientAESEncrypt(firstname),
                "lastname": clientAESEncrypt(lastname)
            }

            // Send to backend and check response
            let res = await axios.put(getGlobalState("backendDomain") + "/api/authorised/updateAccountInfo?sessionID=" + sessionID,
                data, {headers: {Authorization: token}});
            res = clientAESDecrypt(res.data);
            // If successful, reload the page with current details, otherwise display error
            if(res === "Account details updated successfully!"){
                setError("")
                navigate("/viewaccountinfo")
            }else{
                setError(res);
            }
        } catch (resError) {
            setError("Exception occurred when updating account info");
        }
    }

    return (
        <form
            onSubmit={submit}
            className="bg-white text-black shadow-md rounded px-20 pt-6 pb-8 mb-4 flex flex-col"
        >
            <h1 className="text-3xl font-bold mb-10">Update Account Details</h1>
            <div className="mb-4">
                <label className="block text-grey-darker text-sm font-bold mb-2">
                    Email
                </label>
                <input
                    value={email}
                    name="email"
                    onChange={formInputs}
                    className="shadow appearance-none border rounded w-full py-2 px-3 text-grey-darker"
                    type="text"
                    placeholder="1 Somewhere Street Suburb State 1234"
                    required
                />
            </div>
            <div className="mb-6">
                <label className="block text-grey-darker text-sm font-bold mb-2">
                    firstname
                </label>
                <input
                    value={firstname}
                    name="firstname"
                    onChange={formInputs}
                    className="shadow appearance-none border border-red rounded w-full py-2 px-3 text-grey-darker mb-3"
                    type="text"
                    placeholder="firstname"
                    required
                />
            </div>
            <div className="mb-6">
                <label className="block text-grey-darker text-sm font-bold mb-2">
                    lastname
                </label>
                <input
                    value={lastname}
                    name="lastname"
                    onChange={formInputs}
                    className="shadow appearance-none border border-red rounded w-full py-2 px-3 text-grey-darker mb-3"
                    type="text"
                    placeholder="lastname"
                    required
                />
            </div>
            <div className="flex items-center justify-between">
                <button
                    className="bg-blue-500 hover:bg-blue-dark text-white font-bold py-2 px-4 rounded"
                    type="submit">
                    Update Details
                </button>
            </div>
            <h1 className="mt-5 text-red-500">{error}</h1>
        </form>
    );
}
