import '../stylesheets/Auth.css'
import {Link, useLocation, useNavigate} from 'react-router-dom';
import {useState} from "react";

function Auth({ title, buttonName, redirectPage, redirectText, handleSubmit }){
    const [username, setUsername] = useState()
    const [password, setPassword] = useState()
    const [fullname, setFullname] = useState()

    const navigate = useNavigate()
    const location = useLocation()

    const submitHandler= async (e) => {
        e.preventDefault()
        try {
            await handleSubmit(username, password, fullname)
            navigate('/home')
        }
        catch(e) {
            console.log(e)
        }
    }

	return (
    <form id="container-box" onSubmit={submitHandler}>
      <span id="auth-title">{ title }</span>
      <input placeholder="Username" onChange={ e => setUsername(e.target.value) }/>
        { (location.pathname === "/signup") && <input placeholder="Full Name" onChange={ e => setFullname(e.target.value) }/>}
      <input placeholder="Password" type="password" onChange={ e => setPassword(e.target.value)}/>
      <button id="auth-submission" type="submit">
        { buttonName }
      </button>
			<Link to={ redirectPage } id="redirect-button">{redirectText}</Link>
    </form>
  );
}

export default Auth; 