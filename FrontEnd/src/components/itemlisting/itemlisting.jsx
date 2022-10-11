import { getGlobalState, setGlobalState } from "components/utils/globalState";
import axios from "axios";
import { useEffect, useState } from "react";
import { useNavigate, Link, useParams } from "react-router-dom";
import {clientAESDecrypt, clientAESEncrypt,} from "../security/EncryptionUtils";
import {getItemDetails} from "../utils/getItemDetails";
import {addItemToCart} from "../utils/shoppingCart";

export default function Component() {
  const navigate = useNavigate();
  const token = localStorage.getItem("jwt-token");
  const sessionID = localStorage.getItem("sessionID");
  const userID = localStorage.getItem("userID");

  const [product, setProduct] = useState({});
  const queryParams = new URLSearchParams(window.location.search);
  const itemID = queryParams.get("itemID");
  const [confirmDelete, setConfirmDelete] = useState(false);
  const [error, setError] = useState("");

  useEffect(() => {
    // Get item details
    async function fetch1() {
      const decrypted = await JSON.parse(await getItemDetails(itemID));
      setProduct(decrypted);
    }
    fetch1();
  }, []);

  // Add item to cart
  function addToCart(){
    addItemToCart(product);
  }

  // Ask user to confirm disabling their listing
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


        {/*  Allow user to add item to cart if they are signed in & not the seller of the item  */}
        {(userID) && (userID !== product.sellerID) ? (
            <button
                onClick={addToCart}
                className="cursor-pointer inline-flex w-full items-center justify-center rounded-lg bg-white py-2 m font-medium text-black max-w-[20rem]">
              Add to Cart
            </button>
        ) : (
            <div></div>
        )}

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
