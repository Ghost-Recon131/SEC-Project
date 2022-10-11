import { getGlobalState, setGlobalState } from "components/utils/globalState";
import cookie from "js-cookie";
import axios from "axios";
import { useEffect, useState } from "react";
import { useNavigate, Link } from "react-router-dom";

export default function Component() {

    const navigate = useNavigate();
    const token = localStorage.getItem("jwt-token");
    const sessionID = localStorage.getItem("sessionID");

    // Check user is signed in
    useEffect(() => {
        if (!token) {
            navigate("/signin");
        }
    }, []);

    // Variables for storing transactions & purchases
    const [purchaseHistory, setPurchaseHistory] = useState([]);
    const [itemSoldHistory, setItemSoldHistory] = useState({});

    // Get purchase history from backend

    // Get item sold history from backend

    return (
        <div>
            {/* Display purchase history */}
            <h1 className="text-2xl font-bold text-white shadow-md rounded pt-2 pb-8 mb-4">Your purchase history</h1>
            {purchaseHistory.map((purchaseHistory) => (
                <div key={purchaseHistory.transactionID}>
                    <p>Web-store Transaction ID: {purchaseHistory.transactionID}</p>
                    <p>Price: ${purchaseHistory.price} {purchaseHistory.currency}</p>
                    <p>Time of purchase: {purchaseHistory.timeOfPurchase}</p>
                    <p>Seller's ID: {purchaseHistory.sellerID}</p>
                </div>
            ))}

        {/* Display selling history */}
            <h1 className="text-2xl font-bold text-white shadow-md rounded pt-2 pb-8 mb-4">Your purchase history</h1>
            {purchaseHistory.map((purchaseHistory) => (
                <div key={purchaseHistory.transactionID}>
                    <p>Web-store Transaction ID: {purchaseHistory.transactionID}</p>
                    <p>Price: ${purchaseHistory.price} {purchaseHistory.currency}</p>
                    <p>Time of purchase: {purchaseHistory.timeOfPurchase}</p>
                    <p>Buyer's ID: {purchaseHistory.sellerID}</p>
                </div>
            ))}
        </div>
    );
}
