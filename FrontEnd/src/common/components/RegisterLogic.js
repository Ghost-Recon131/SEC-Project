import url from '../../url'
import axios from 'axios'

async function registerLogic(username, password, fullname){
    const sessionID = sessionStorage.getItem('sessionID')

    const token = await axios.post(`http://localhost:8080/api/RegisterLogin/register?sessionID=` + sessionID, {
        username: username,
        fullName: fullname,
        password: password
    })
}

export default registerLogic