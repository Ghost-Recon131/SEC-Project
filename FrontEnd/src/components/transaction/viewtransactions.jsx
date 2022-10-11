import { getGlobalState, setGlobalState } from "components/utils/globalState";
import axios from "axios";
import { useEffect, useState } from "react";
import { useNavigate, Link } from "react-router-dom";
import {clientAESDecrypt} from "../security/EncryptionUtils";

export default function Component() {

    const navigate = useNavigate();
    const token = localStorage.getItem("jwt-token");
    const sessionID = localStorage.getItem("sessionID");

    // Declare functions
    // Variables for storing transactions & purchases
    const [purchaseHistory, setPurchaseHistory] = useState([]);
    const [itemSoldHistory, setItemSoldHistory] = useState([]);
    const nullHistory = {"transactionID": "", "itemID": "", "sellerID": "", "buyerID": "", "totalCost": "", "transactionDate": ""}


    // Check user is signed in
    useEffect(() => {
        if (!token) {
            navigate("/signin");
        }else{
            getTransactionHistory("/api/authorised/transactions/getAllPurchases?sessionID=", setPurchaseHistory);
            getTransactionHistory("/api/authorised/transactions/getAllSales?sessionID=", setItemSoldHistory);
        }
    }, []);



    // Get purchase history from backend
    async function getTransactionHistory(URI, toUpdate){
        try{
            let encryptedPurchaseHistory = await axios.get(getGlobalState("backendDomain") + URI + sessionID,
                { headers: { Authorization: token } });
            encryptedPurchaseHistory = encryptedPurchaseHistory.data;

            // Check if there is any history
            if(encryptedPurchaseHistory.length !== 0){
                // Decrypt data and save to arraylist
                let tmpTransactions = []
                encryptedPurchaseHistory.forEach((item) => {
                    item.transactionID = clientAESDecrypt(item.transactionID);
                    item.itemID = clientAESDecrypt(item.itemID);
                    item.sellerID = clientAESDecrypt(item.sellerID);
                    item.buyerID = clientAESDecrypt(item.buyerID);
                    item.totalCost = clientAESDecrypt(item.totalCost);
                    item.transactionDate = clientAESDecrypt(item.transactionDate);
                    tmpTransactions.push(item);
                });
                toUpdate(tmpTransactions);
            }else{
                const tmpList = [];
                tmpList.push(nullHistory)
                toUpdate(tmpList);
            }
        }catch (e){
            console.log("Failed to retrieve purchase history: ", e);
        }
    }

    return (
        <div>
            {/* Display purchase history */}
            <h1 className="text-2xl font-bold text-white shadow-md rounded pt-2 pb-8 mb-4">Your purchase history</h1>
            {purchaseHistory.map((purchaseHistory) => (
                <div key={purchaseHistory.transactionID}>
                    <p>Web-store Transaction ID: {purchaseHistory.transactionID}</p>
                    <p>Item ID: {purchaseHistory.itemID}</p>
                    <p>Price: ${purchaseHistory.price} {purchaseHistory.totalCost}</p>
                    <p>Time of purchase: {purchaseHistory.transactionDate}</p>
                    <p>Seller's ID: {purchaseHistory.sellerID}</p>
                </div>
            ))}

           <br></br><br></br>

        {/* Display selling history */}
            <h1 className="text-2xl font-bold text-white shadow-md rounded pt-2 pb-8 mb-4">Your selling history</h1>
            {itemSoldHistory.map((itemSoldHistory) => (
                <div key={itemSoldHistory.transactionID}>
                    <p>Web-store Transaction ID: {itemSoldHistory.transactionID}</p>
                    <p>Item ID: {itemSoldHistory.itemID}</p>
                    <p>Price: ${itemSoldHistory.price} {itemSoldHistory.totalCost}</p>
                    <p>Time of purchase: {itemSoldHistory.transactionDate}</p>
                    <p>Buyer's ID: {itemSoldHistory.buyerID}</p>
                </div>
            ))}
        </div>
    );
}
