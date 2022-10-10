import { getGlobalState, setGlobalState } from "components/utils/globalState";
import axios from "axios";
import { useState } from "react";
import {useNavigate,} from 'react-router-dom';
import{clientAESEncrypt, clientAESDecrypt} from "../security/EncryptionUtils";


export default function Component() {
    var [formData, setFormData] = useState({ username: "", secretQuestion: "", secretQuestionAnswer: "" , newPassword: "", confirmNewPassword: ""});
    var { username, secretQuestion, secretQuestionAnswer, newPassword, confirmNewPassword } = formData;
    var [error, setError] = useState("");
    const navigate = useNavigate();

    function formInputs(event) {
        event.preventDefault();
        var { name, value } = event.target;
        setFormData({ ...formData, [name]: value });
    }

    async function submit(event) {
        event.preventDefault();

        // Check the password and confirm password fields match
        if(newPassword !== confirmNewPassword){
            setError("Passwords do not match");
        }else{
            // If they match, empty any possible error text then send the request to the server
            setError("");
            try {
                const data = {
                    "username": clientAESEncrypt(username),
                    "secret_question": clientAESEncrypt(secretQuestion),
                    "secret_question_answer": clientAESEncrypt(secretQuestionAnswer),
                    "newPassword": clientAESEncrypt(newPassword)
                }
                const sessionID = localStorage.getItem('sessionID');
                var res = await axios.post(getGlobalState("backendDomain")+"/api/authorised/viewAccountInfo?sessionID=" + sessionID, data);

                // Check response from server, then redirect to log in on success or display error on failure
                res = clientAESDecrypt(res.data);
                if(res === "Password successfully changed") {
                    setError("Password reset successfully");
                    navigate("/login");
                }else{
                    setError(res);
                }
            }catch (resError) {
                setError("Exception occurred while resetting password");
            }
        }
    }

    return (
        <form
            onSubmit={submit}
            className="bg-white text-black shadow-md rounded px-20 pt-6 pb-8 mb-4 flex flex-col"
        >
            <h1 className="text-3xl font-bold mb-10">Forgot Password</h1>
            <div className="mb-4">
                <label className="block text-grey-darker text-sm font-bold mb-2">
                    Account Email
                </label>
                <input
                    value={username}
                    name="username"
                    onChange={formInputs}
                    className="shadow appearance-none border rounded w-full py-2 px-3 text-grey-darker"
                    type="text"
                    placeholder="someone@gmail.com"
                    required
                />
            </div>
            <div className="mb-4">
                <label className="block text-grey-darker text-sm font-bold mb-2">
                    Secret Question
                </label>
                <input
                    value={secretQuestion}
                    name="secretQuestion"
                    onChange={formInputs}
                    className="shadow appearance-none border rounded w-full py-2 px-3 text-grey-darker"
                    type="text"
                    placeholder="What is... "
                    required
                />
            </div>
            <div className="mb-6">
                <label className="block text-grey-darker text-sm font-bold mb-2">
                Secret Question Answer
                </label>
                <input
                    value={secretQuestionAnswer}
                    name="secretQuestionAnswer"
                    onChange={formInputs}
                    className="shadow appearance-none border border-red rounded w-full py-2 px-3 text-grey-darker mb-3"
                    type="password"
                    placeholder="secret Question Answer"
                    required
                />
            </div>
            <div className="mb-6">
                <label className="block text-grey-darker text-sm font-bold mb-2">
                    New Password
                </label>
                <input
                    value={newPassword}
                    name="newPassword"
                    onChange={formInputs}
                    className="shadow appearance-none border border-red rounded w-full py-2 px-3 text-grey-darker mb-3"
                    type="password"
                    placeholder="your new password"
                    required
                />
            </div>
            <div className="mb-6">
                <label className="block text-grey-darker text-sm font-bold mb-2">
                    Confirm New Password
                </label>
                <input
                    value={confirmNewPassword}
                    name="confirmNewPassword"
                    onChange={formInputs}
                    className="shadow appearance-none border border-red rounded w-full py-2 px-3 text-grey-darker mb-3"
                    type="password"
                    placeholder="Confirm your new password"
                    required
                />
            </div>

            <div className="flex items-center justify-between">
                <button
                    className="bg-blue-500 hover:bg-blue-dark text-white font-bold py-2 px-4 rounded"
                    type="submit"
                >
                    Reset Password
                </button>
            </div>
            <h1 className="mt-5 text-red-500">{error}</h1>
        </form>
    );
}
