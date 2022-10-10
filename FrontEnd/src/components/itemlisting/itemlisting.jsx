import { getGlobalState, setGlobalState } from "components/utils/globalState";
import axios from "axios";
import { useEffect, useState } from "react";
import { useNavigate, Link, useParams } from "react-router-dom";
import {
  clientAESDecrypt,
  clientAESEncrypt,
} from "../security/EncryptionUtils";

export default function Component() {
  const navigate = useNavigate();
  const token = localStorage.getItem("jwt-token");
  const sessionID = localStorage.getItem("sessionID");
  const userID = localStorage.getItem("userID");

  var [product, setProduct] = useState({});
  var [images, setImages] = useState([]);
  var queryParams = new URLSearchParams(window.location.search);
  var itemID = queryParams.get("itemID");
  var [confirmDelete, setConfirmDelete] = useState(false);
  const [error, setError] = useState("");

  useEffect(() => {
    // Get item details
    async function fetch1() {
      const data = {
        itemID: clientAESEncrypt(itemID),
        sellerID: "",
        category: "",
      };

      let item = await axios.post(
        getGlobalState("backendDomain") +
          "/api/catalogue/viewItem?sessionID=" +
          sessionID,
        data
      );
      item = item.data;

      const decrypted = {
        itemID: clientAESDecrypt(item.itemID),
        sellerID: clientAESDecrypt(item.sellerID),
        itemName: clientAESDecrypt(item.itemName),
        itemDescription: clientAESDecrypt(item.itemDescription),
        itemAvailable: clientAESDecrypt(item.itemAvailable),
        itemPrice: clientAESDecrypt(item.itemPrice),
        itemQuantity: clientAESDecrypt(item.itemQuantity),
        itemImage: clientAESDecrypt(item.itemImage),
        itemCategory: clientAESDecrypt(item.itemCategory),
      };
      setProduct(decrypted);
    }
    fetch1();
  }, []);

  // Set
  function confirmDisableListing() {
    if (token) {
      const confirm = true;
      setConfirmDelete(confirm);
    } else {
      setError("You must be signed in to modify a listing");
    }
  }

  // Function to disable a listing
  async function disableListing() {
    try{
      const data = {
        itemID: clientAESEncrypt(itemID),
        sellerID: "",
        category: "",
      };

      let response = await axios.post(
          getGlobalState("backendDomain") +
          "/api/authorised/catalogue/endListing?sessionID=" +
          sessionID,
          data,
          {headers: {Authorization: token}}
      );

      response = clientAESDecrypt(response.data);

      // Go back to home after delete
      if(response === "Successfully disabled item listing"){
        setError("");
        navigate("/");
      }else{
        setError(response);
      }
    }catch (e) {
      setError("Exception occurred");
    }
  }

  return (
    <div className="bg-black text-white flex">
      <img
        className="h-50 w-96 rounded-lg object-cover mr-6"
        src={product.itemImage}
      />
      <div className="w-6/12">
        <h1 className="text-2xl mb-8">{product.itemName}</h1>
        <p className="mb-4">Category: {product.itemCategory}</p>
        <p className="mb-4">Description: {product.itemDescription}</p>
        <p className="mb-4">Price: ${product.itemPrice}</p>
        <p className="mb-4">Item Available: {product.itemAvailable}</p>
        <p className="mb-4">Remaining stock: {product.itemQuantity}</p>
        {/*If: logged-in user == creator of item listing, show below*/}
        {userID === product.sellerID ? (
          <div>
            <button
              className="text-yellow-400 mb-4"
              onClick={() => navigate("/editListing?itemID=" + product.itemID)}
            >
              Edit Listing
            </button>
            <br></br>
            <button
              className="text-red-600 font-bold"
              onClick={() => confirmDisableListing()}
            >
              Disable Listing (WARNING THIS PROCESS IS IRREVERSIBLE)
            </button>

            {/* If: logged-in user == creator of item listing and confirmed to delete account */}
            {confirmDelete === true ? (
              <div>
                <button
                  className="text-red-600 font-bold"
                  onClick={() => disableListing()}>
                  CONFIRM DISABLE
                </button>
                <p>{error}</p>
              </div>
            ) : (
              <div className="text-yellow-500 font-bold"></div>
            )}
          </div>
        ) : (
          <div className="text-yellow-500 font-bold"></div>
        )}
      </div>
    </div>
  );
}
