import { getGlobalState, setGlobalState } from "components/utils/globalState";
import cookie from "js-cookie";
import axios from "axios";
import { useEffect, useState } from "react";
import { useNavigate, Link, useParams } from "react-router-dom";
import {clientAESDecrypt, clientAESEncrypt} from "../security/EncryptionUtils";

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


  // Setup variables to list new items
  const [formData, setFormData] = useState({
    itemID: "",
    itemName: "",
    itemDescription: "",
    itemAvailable: "",
    itemPrice: "",
    itemQuantity: "",
    itemImage: "",
    category: "OTHER"
  });
  const {itemName, itemDescription, itemPrice, itemQuantity, itemImage, category} = formData;
  const [error, setError] = useState("");
  const [confirm, setConfirm] = useState(false);

  // Part of drop down menu for item condition
  let itemCategory = [
    {label: "ELECTRONICS", value: "ELECTRONICS" },
    {label: "BOOKS", value: "BOOKS" },
    {label: "CLOTHING", value: "CLOTHING" },
    {label: "GAMES", value: "GAMES"},
    {label: "FOOD", value: "FOOD"},
    {label: "OTHER", value: "OTHER"}
  ]

  let handleItemCategoryChange = (e) => {
    setFormData({...formData, category: e.target.value});
  }

  function formInputs(event) {
    event.preventDefault();
    var { name, value } = event.target;
    setFormData({ ...formData, [name]: value });
  }


  async function formSubmit(event) {
    event.preventDefault();
    try {
      const data = {
        "itemID": clientAESEncrypt("NULL"),
        "itemName": clientAESEncrypt(itemName),
        "itemDescription": clientAESEncrypt(itemDescription),
        "itemAvailable": clientAESEncrypt("NULL"),
        "itemPrice": clientAESEncrypt(itemPrice),
        "itemQuantity": clientAESEncrypt(itemQuantity),
        "itemImage": clientAESEncrypt(itemImage),
        "itemCategory": clientAESEncrypt(category)
      }

      console.table("JSON OBJECT" + JSON.stringify(data));

      // Send listing data to backend
      let response = await axios.post(getGlobalState("backendDomain") + "/api/authorised/catalogue/listItem?sessionID=" + sessionID,
          data, {headers: {Authorization: token}});
      response = clientAESDecrypt(response.data);

      // If response is success, redirect user to home page, otherwise display error
      if(response === "Successfully listed item"){
        setError("")
        navigate("/");
      }else{
        setError(response);
      }
    }catch (resError) {
      setError("Exception occurred while listing item");
      console.log("Item listing exception: \n", resError);
    }
  }

  return (
      <form
          onSubmit={formSubmit}
          className="bg-white text-black shadow-md rounded px-96 pt-6 pb-8 mb-4 flex flex-col">
        <h1 className="text-3xl font-bold mb-10">Create new item listing</h1>
        <div className="mb-4">
          <label className="block text-grey-darker text-sm font-bold mb-2">
            Listing Title
          </label>
          <input
              value={itemName}
              name="itemName"
              onChange={formInputs}
              className="shadow appearance-none border rounded w-full py-2 px-3 text-grey-darker"
              type="text"
              placeholder="interesting title or name of what you are selling"
              required/>
        </div>
        <div className="mb-6">
          <label className="block text-grey-darker text-sm font-bold mb-2">
            Description
          </label>
          <input
              value={itemDescription}
              name="itemDescription"
              onChange={formInputs}
              className="shadow appearance-none border border-red rounded w-full py-2 px-3 text-grey-darker mb-3"
              type="text"
              placeholder="tell us about this item"
              required
          />
        </div>
        <div className="mb-6">
          <label className="block text-grey-darker text-sm font-bold mb-2">
          Price (in AUD)
          </label>
          <input
              value={itemPrice}
              name="itemPrice"
              onChange={formInputs}
              className="shadow appearance-none border border-red rounded w-full py-2 px-3 text-grey-darker mb-3"
              type="text"
              placeholder="42.55"
              required
          />
        </div>
        <div className="mb-6">
          <label className="block text-grey-darker text-sm font-bold mb-2">
            Quantity
          </label>
          <input
              value={itemQuantity}
              name="itemQuantity"
              onChange={formInputs}
              className="shadow appearance-none border border-red rounded w-full py-2 px-3 text-grey-darker mb-3"
              type="number"
              placeholder="42"
              required
          />
        </div>
        <div className="mb-6">
          <label className="block text-grey-darker text-sm font-bold mb-2">
            Image URL
          </label>
          <input
              value={itemImage}
              name="itemImage"
              onChange={formInputs}
              className="shadow appearance-none border border-red rounded w-full py-2 px-3 text-grey-darker mb-3"
              type="text"
              placeholder="https://www.example.com/image.png"
              required
          />
        </div>
        <div className="mb-6">
          <label className="block text-grey-darker text-sm font-bold mb-2">
            Item Category
          </label>
          <select onChange={handleItemCategoryChange}>
            <option disabled> -- Select category that best fits your item -- </option>
            {
              itemCategory.map((item) => <option value={item.label}>{item.value}</option>)
            }
          </select>
        </div>
        <button
            className="bg-blue-500 hover:bg-blue-dark text-white font-bold py-2 px-4 rounded"
            type="submit">
          Create listing
        </button>

        <h1 className="mt-5 text-red-500">{error}</h1>
      </form>
  );
}
