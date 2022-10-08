import Auth from "../common/components/Auth";
import registerLogic from "../common/components/RegisterLogic";
import {useEffect} from "react";
import {useNavigate} from "react-router-dom";

function SignUp() {
  const navigate = useNavigate()
  useEffect(() => {
    // if (sessionStorage.getItem('user-token'))
    //   navigate('/home')
  })
  return (
    <Auth 
      title="Sign Up" 
      buttonName="Register" 
      redirectPage="/login" 
      redirectText="Already have an account? Sign in!"
      handleSubmit={registerLogic}
    />
  );
}

export default SignUp;