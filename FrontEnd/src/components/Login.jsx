import Auth from "../common/components/Auth";
import loginLogic from "../common/components/LoginLogic"
import {useEffect} from "react";
import {useNavigate} from "react-router-dom";

function Login() {

  // Check that we have valid session key, if not go to home page & perform key exchange
  const navigate =useNavigate()
  useEffect(() => {
    // if (sessionStorage.getItem('sessionID')){
    //   navigate('/home')
    // }
  })
  return (
    <Auth 
      title="Login"
      buttonName="Sign in"
      redirectPage="/signup" 
      redirectText="Don't have an account? Sign up!"
      handleSubmit={loginLogic}
    />
  );
}

export default Login;