import '../stylesheets/Auth.css'
import {Link, useLocation, useNavigate} from 'react-router-dom';
import {useState} from "react";

function Auth({ title, buttonName, redirectPage, redirectText, handleSubmit }){
    var message;
    const [username, setUsername] = useState()
    const [email, setEmail] = useState()
    const [firstname, setFirstname] = useState()
    const [lastname, setLastname] = useState()
    const [password, setPassword] = useState()
    const [secret_question, setSecretQuestion] = useState()
    const [secret_answer, setSecretAnswer] = useState()

    const navigate = useNavigate()
    const location = useLocation()

    const submitHandler= async (e) => {
        e.preventDefault()
        try {
            const response = await handleSubmit(username, email, firstname, lastname, password, secret_question, secret_answer);
            if (response !== null) {
                // Verify register status
                if(response === "Signup Successful"){
                    navigate('/login')
                }else if(response.startsWith("Bearer ")){
                    // redirect to product home page
                    navigate('/products')
                }else{
                    message = response
                }
            }else{
                message = "Error"
            }
        }
        catch(e) {
            console.log(e)
        }
    }

	return (
    <form id="container-box" onSubmit={submitHandler}>
      <span id="auth-title">{ title }</span>
      <input placeholder="Username" onChange={ e => setUsername(e.target.value) }/>
        {/* Additional fields that only show up when the page is "Sign Up" */}
        {(location.pathname === "/signup") && <input placeholder="email" onChange={ e => setEmail(e.target.value) }/>}
        {(location.pathname === "/signup") && <input placeholder="First Name" onChange={ e => setFirstname(e.target.value) }/>}
        {(location.pathname === "/signup") && <input placeholder="Last Name" onChange={ e => setLastname(e.target.value) }/>}
      <input placeholder="Password" type="password" onChange={ e => setPassword(e.target.value)}/>
        {(location.pathname === "/signup") && <input placeholder="Enter secret question (case sensitive)" onChange={ e => setSecretQuestion(e.target.value) }/>}
        {(location.pathname === "/signup") && <input placeholder="Enter secret answer (case sensitive)" onChange={ e => setSecretAnswer(e.target.value) }/>}
      <button id="auth-submission" type="submit">
        { buttonName }
      </button>
			<Link to={ redirectPage } id="redirect-button">{redirectText}</Link>
        <p id="status">{message}</p>
    </form>
  );
}

export default Auth; 