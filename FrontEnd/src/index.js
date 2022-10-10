import React from "react";
import "index.css";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import { createRoot } from 'react-dom/client';
import Home from "components/general/home";
import ItemListing from "components/itemlisting/itemlisting";
import NewListing from "components/itemlisting/newlisting";
import SignIn from "components/general/signin";
import Register from "components/general/register";
import ForgotPassword from "components/general/forgotpassword";
import NavBar from "components/general/navbar";
import Footer from "components/general/footer";
import ModifyAccountInfo from "components/general/modifyaccountinfo";
import ViewAccountInfo from "components/general/viewaccountinfo";
import EditListing from "components/itemlisting/editlisting";
import ViewTransactions from "components/transaction/viewtransactions"
import Cart from "components/transaction/cart";


createRoot(
  document.getElementById("root")
).render(
  <div className="bg-black text-white px-20 pb-80">
    <BrowserRouter>
      <NavBar />
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/itemListing" element={<ItemListing />} />
        <Route path="/newListing" element={<NewListing />} />
        <Route path="/signin" element={<SignIn />} />
        <Route path="/register" element={<Register />} />
        <Route path="/forgotpassword" element={<ForgotPassword />} />
        <Route path="/modifyaccountinfo" element={<ModifyAccountInfo />} />
        <Route path="/viewaccountinfo" element={<ViewAccountInfo />} />
        <Route path="/editlisting" element={<EditListing />} />
        <Route path="/viewtransactions" element={<ViewTransactions />} />
        <Route path="/cart" element={<Cart />} />
      </Routes>
      <Footer />
    </BrowserRouter>
  </div>,
);
