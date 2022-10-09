import { getGlobalState, setGlobalState } from "components/utils/globalState";
import axios from "axios";
import { useState } from "react";
import cookie from "js-cookie";
import { useNavigate } from "react-router-dom";
import {clientAESDecrypt, clientAESEncrypt} from "../security/EncryptionUtils";

export default function Component() {
  var [formData, setFormData] = useState({ username: "", password: "" });
  var { username, password } = formData;
  var [error, setError] = useState("");
  const navigate = useNavigate();

  function formInputs(event) {
    event.preventDefault();
    var { name, value } = event.target;
    setFormData({ ...formData, [name]: value});
  }

  async function formSubmit(event) {
    event.preventDefault();
    const sessionID = sessionStorage.getItem('sessionID');

    // Encrypt our data
    const data = {
        "username": clientAESEncrypt(username),
        "password": clientAESEncrypt(password),
    }

    // Post login info to backend
    try{
      const res = await axios.post(getGlobalState("backendDomain") + "/api/RegisterLogin/login?sessionID=" + sessionID, data);
      const token = res.data.token;
      // Set the JWT token in session storage, then take user back to home
      if(token.startsWith("Bearer ")){
        sessionStorage.setItem('jwt-token', token)

        // Get the user's account info using JWT token
        const getUserDetails = await axios.get(getGlobalState("backendDomain") + "/api/authorised/viewAccountInfo?sessionID=" + sessionID, {headers: {Authorization: token}});
        let encryptedUserDetails = getUserDetails.data
        sessionStorage.setItem("user", clientAESDecrypt(encryptedUserDetails.email))
        // Take user back to home after successful login
        navigate("/")
        setError("");
      }
    }catch(e){
      // Will throw exception if login is invalid
      setError("Invalid credentials");
    }
  }

  return (
    <form
      onSubmit={formSubmit}
      className="bg-white text-black shadow-md rounded px-96 pt-6 pb-8 mb-4 flex flex-col"
    >
      <h1 className="text-3xl font-bold mb-10">Sign in</h1>
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
      <div className="flex items-center justify-between">
        <button
          className="bg-blue-500 hover:bg-blue-dark text-white font-bold py-2 px-4 rounded"
          type="submit"
        >
          Sign In
        </button>
      </div>
      <h1 className="mt-5 text-red-500">{error}</h1>
    </form>
  );
}