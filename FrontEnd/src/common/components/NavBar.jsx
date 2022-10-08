import React from "react";
import { Link, useLocation, useNavigate } from "react-router-dom";
import '../stylesheets/NavBar.css'

function NavBar() {
  const location = useLocation()
  const navigate = useNavigate()

  const logoutHandler = () => {
    sessionStorage.removeItem('user-token')
    navigate('/login')
  }

  const checkoutHandler = () => {
    console.log('checkout handler goes here')
    navigate('/transaction')
  }

  const homeButton = <Link to="/home" id="home-button" className="navbar-button">Home</Link>;
  const signupButton = <Link to='/signup' id="sign-in-button" className="navbar-button">Sign-up</Link>
  const loginButton = <Link to="/login" id="login-button" className="navbar-button">Login</Link>
  const logoutButton = <span id="logout-button" className="navbar-button" onClick={logoutHandler}>Logout</span>
  const checkoutButton = <span id="checkout-button" className="navbar-button" onClick={checkoutHandler}>Checkout</span>
  const cartButton = <Link to="/cart" id="cart-button" className="navbar-button" >Cart</Link>

  if (!sessionStorage.getItem('user-token'))
    return (
      <nav>
        { homeButton }
        { signupButton }
        { loginButton }
      </nav>
    );
  else if ( location.pathname === "/cart" )
    return (
      <nav>
        { homeButton }
        { checkoutButton }
        { logoutButton }
      </nav>
    );
  else
    return (
      <nav>
        { homeButton }
        { cartButton }
        { logoutButton }
      </nav>
    );
}

export default NavBar;