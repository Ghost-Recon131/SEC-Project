import React, {useEffect} from "react";
import NavBar from "../common/components/NavBar";
import StartPage from "../common/components/StartPage";
import './Home.css'
import Axios from "axios";
import {useNavigate} from "react-router-dom";
import keyExchange from "../common/components/KeyExchange";

function Home() {
    const navigate = useNavigate()
    useEffect(() => {
        if (!sessionStorage.getItem('sessionID')){
            keyExchange()
            console.log("key exchange")
        }
    })
    return (
        <div id="home">
            <NavBar />
            {!sessionStorage.getItem('jwt-token') && <StartPage /> }
        </div>
    );
}

export default Home;