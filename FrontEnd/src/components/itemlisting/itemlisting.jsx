import { getGlobalState, setGlobalState } from "components/utils/globalState";
import cookie from "js-cookie";
import axios from "axios";
import { useEffect, useState } from "react";
import { useNavigate, Link, useParams } from "react-router-dom";
import { data } from "autoprefixer";
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
  function confirmDeleteListing() {
    if (!token) {
      const confirm = true;
      setConfirmDelete(confirm);
    } else {
      setError("You must be signed in to modify a listing");
    }
  }

  // TODO: Fix here
  // Function to disable a listing
  async function deleteListing() {
    const data = {
      itemID: clientAESEncrypt(itemID),
      sellerID: "",
      category: "",
    };

    let response = await axios.post(
      getGlobalState("backendDomain") +
        "/api/authorised/catalogue/?sessionID=" +
        sessionID,
      data
    );

    // Go back to home after delete
    // navigate("/");
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
        <p className="mb-4">Remaning stock: {product.itemQuantity}</p>
        {/*If: logged-in user == creator of item listing, show below*/}
        {userID === product.sellerID ? (
          <div>
            <button
              className="text-yellow-400 mb-4"
              onClick={() => navigate("/editListing?id=" + product.id)}
            >
              Edit Listing
            </button>
            <br></br>
            <button
              className="text-red-600 font-bold"
              onClick={() => confirmDeleteListing()}
            >
              Disable Listing
            </button>

            {/* If: logged-in user == creator of item listing and confirmed to delete account */}
            {confirmDelete === true ? (
              <div>
                <button
                  className="text-red-600 font-bold"
                  onClick={() => deleteListing()}
                >
                  CONFIRM DISABLE
                </button>
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
