import axios from "axios";
import { useState } from "react";
import {useNavigate,} from 'react-router-dom';
import {getGlobalState} from "../utils/globalState";
import {clientAESEncrypt, clientAESDecrypt} from "../security/EncryptionUtils";


export default function Component() {
    var [formData, setFormData] = useState({username: "", email: "", firstname: "", lastname: "", password: "", confirmPassword: "", secretQuestion: "", secretQuestionAnswer: "" });
    var { username, email, firstname, lastname, password, confirmPassword, secretQuestion, secretQuestionAnswer } = formData;
    var [error, setError] = useState("");
    const navigate = useNavigate();

    function formInputs(event) {
        event.preventDefault();
        var { name, value } = event.target;
        setFormData({ ...formData, [name]: value });
    }

    async function submit(event) {
        event.preventDefault();
        if(confirmPassword !== password){
            setError("Your passwords do not match");
        }else{
            setError("")
            try {
                const data = {
                    "username": clientAESEncrypt(username),
                    "email": clientAESEncrypt(email),
                    "firstname": clientAESEncrypt(firstname),
                    "lastname": clientAESEncrypt(lastname),
                    "password": clientAESEncrypt(password),
                    "secret_question": clientAESEncrypt(secretQuestion),
                    "secret_question_answer": clientAESEncrypt(secretQuestionAnswer)
                };
                const sessionID = sessionStorage.getItem('sessionID')
                var res = await axios.post(getGlobalState("backendDomain") + "/api/RegisterLogin/register?sessionID=" + sessionID, data);
                res = clientAESDecrypt(res.data);
                if (res === "Signup Successful") {
                    setError("Account created successfully");
                    navigate("/signin");
                }else{
                    setError(res)
                }
            } catch (e) {
                setError(e)
            }
        }
    }

    return (
        <form
            onSubmit={submit}
            className="bg-white text-black shadow-md rounded px-20 pt-6 pb-8 mb-4 flex flex-col"
        >
            <h1 className="text-3xl font-bold mb-10">Register</h1>
            <div className="mb-4">
                <label className="block text-grey-darker text-sm font-bold mb-2">
                    Firstname
                </label>
                <input
                    value={firstname}
                    name="firstname"
                    onChange={formInputs}
                    className="shadow appearance-none border rounded w-full py-2 px-3 text-grey-darker"
                    type="text"
                    placeholder="John Person"
                    required
                />
            </div>
            <div className="mb-4">
                <label className="block text-grey-darker text-sm font-bold mb-2">
                    lastname
                </label>
                <input
                    value={lastname}
                    name="lastname"
                    onChange={formInputs}
                    className="shadow appearance-none border rounded w-full py-2 px-3 text-grey-darker"
                    type="text"
                    placeholder="John Person"
                    required
                />
            </div>
            <div className="mb-4">
                <label className="block text-grey-darker text-sm font-bold mb-2">
                    Username
                </label>
                <input
                    value={username}
                    name="username"
                    onChange={formInputs}
                    className="shadow appearance-none border rounded w-full py-2 px-3 text-grey-darker"
                    type="text"
                    placeholder="Username"
                    required
                />
            </div>
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
                    placeholder="email"
                    required
                />
            </div>
            <div className="mb-6">
                <label className="block text-grey-darker text-sm font-bold mb-2">
                    Password
                </label>
                <input
                    value={password}
                    name="password"
                    onChange={formInputs}
                    className="shadow appearance-none border border-red rounded w-full py-2 px-3 text-grey-darker mb-3"
                    type="password"
                    placeholder="Password"
                    required
                />
            </div>
            <div className="mb-6">
                <label className="block text-grey-darker text-sm font-bold mb-2">
                    Confirm Password
                </label>
                <input
                    value={confirmPassword}
                    name="confirmPassword"
                    onChange={formInputs}
                    className="shadow appearance-none border border-red rounded w-full py-2 px-3 text-grey-darker mb-3"
                    type="password"
                    placeholder="confirmPassword"
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
                secretQuestionAnswer
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
            <div className="flex items-center justify-between">
                <button
                    className="bg-blue-500 hover:bg-blue-dark text-white font-bold py-2 px-4 rounded"
                    type="submit"
                >
                    Register Account
                </button>
            </div>
            <h1 className="mt-5 text-red-500">{error}</h1>
        </form>
    );
}
