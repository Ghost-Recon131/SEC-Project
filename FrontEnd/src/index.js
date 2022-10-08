import StartPage from './components/Home'
import React, {useEffect} from 'react';
import ReactDOM from 'react-dom/client';
import { BrowserRouter, Navigate, Route, Routes } from 'react-router-dom';
import SignUp from './components/SignUp';
import Login from './components/Login';
// import Cart from './components/Cart'
import './common/stylesheets/Base.css'

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <BrowserRouter>
    <Routes>
      <Route path="/StartPage" element={<StartPage />} />
      <Route path="/signup" element={ <SignUp /> }  />
      <Route path="/login" element={ <Login /> } />
      {/*<Route path="/cart" element={ <Cart /> } />*/}
      <Route path="*" element={<Navigate replace to="/StartPage" />} />
    </Routes>
  </BrowserRouter>
);


