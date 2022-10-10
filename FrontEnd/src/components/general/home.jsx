import axios from "axios";
import { useEffect, useState } from "react";
import { useNavigate, Link } from "react-router-dom";
import keyExchange from "../security/KeyExchange";
import { clientAESDecrypt } from "../security/EncryptionUtils";
import { getGlobalState } from "../utils/globalState";
import Product from "./product";

export default function Component() {
  const [items, setItems] = useState([]);
  const space = " ";

  useEffect(() => {
    if (!localStorage.getItem("sessionID")) {
      keyExchange().then((r) => window.location.reload());
      // window.location.reload()
      console.log("key exchange");
    } else {
      // Get our session ID
      const sessionID = localStorage.getItem("sessionID");

      // Get encrypted data from backend, then decrypt it
      async function axiosPost() {
        const res = await axios.get(
          getGlobalState("backendDomain") +
            "/api/catalogue/allItems?sessionID=" +
            sessionID
        );
        let tempProduct = [];
        res.data.forEach((item) => {
          item.itemID = clientAESDecrypt(item.itemID);
          item.sellerID = clientAESDecrypt(item.sellerID);
          item.itemName = clientAESDecrypt(item.itemName);
          item.itemDescription = clientAESDecrypt(item.itemDescription);
          item.itemAvailable = clientAESDecrypt(item.itemAvailable);
          item.itemPrice = clientAESDecrypt(item.itemPrice);
          item.itemQuantity = clientAESDecrypt(item.itemQuantity);
          item.itemImage = clientAESDecrypt(item.itemImage);
          item.itemCategory = clientAESDecrypt(item.itemCategory);
          tempProduct.push(item);
        });
        setItems(tempProduct);
      }
      axiosPost();
    }
  }, []);

  // Display our products
  return (
    <div>
      <div className="flex flex-wrap text-white">
        {items.map((item) => (
          <Product item={item} key={item.itemID} />
        ))}
      </div>
    </div>
  );
}
