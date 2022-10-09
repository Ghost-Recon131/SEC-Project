import axios from "axios";
import { useEffect, useState } from "react";
import { useNavigate, Link } from "react-router-dom";
import keyExchange from "../security/KeyExchange";
import {clientAESDecrypt} from "../security/EncryptionUtils";
import {getGlobalState} from "../utils/globalState";

export default function Component() {
  const [itemListings, setItemListings] = useState([]);
  const space = " "

  useEffect(() => {
    if (!sessionStorage.getItem('sessionID')){
      keyExchange().then(r => window.location.reload())
      // window.location.reload()
      console.log("key exchange")
    }else{
      // Get our session ID
      const sessionID = sessionStorage.getItem('sessionID')

      // Get encrypted data from backend, then decrypt it
      async function axiosPost() {
        const res = await axios.get(getGlobalState("backendDomain") + "/api/catalogue/allItems?sessionID=" + sessionID);
        let tempProduct = []
        res.data.forEach((item) => {
          item.itemID = clientAESDecrypt(item.itemID)
          item.sellerID = clientAESDecrypt(item.sellerID);
          item.itemName = clientAESDecrypt(item.itemName);
          item.itemDescription = clientAESDecrypt(item.itemDescription);
          item.itemAvailable = clientAESDecrypt(item.itemAvailable);
          item.itemPrice = clientAESDecrypt(item.itemPrice);
          item.itemQuantity = clientAESDecrypt(item.itemQuantity);
          item.itemImage = clientAESDecrypt(item.itemImage);
          item.itemCategory = clientAESDecrypt(item.itemCategory);
          tempProduct.push(item);
        })
        setItemListings(tempProduct)
      }
      axiosPost();
    }
  }, []);

  // Display our products
  return (
    <div>
      <h3 className="text-2xl font-bold text-white shadow-md rounded pt-2 pb-8 mb-4">Item Listings</h3>
      {itemListings.map((itemListing) => (
        <div key={itemListing.itemID}>
          <Link to={"/itemListing?itemID=" + itemListing.itemID}>
            {itemListing.itemName} {space}
            ${itemListing.itemPrice}
            {itemListing.itemImage}
          </Link>
          <p></p>
          <br></br>
        </div>
      ))}
    </div>
  );
}
