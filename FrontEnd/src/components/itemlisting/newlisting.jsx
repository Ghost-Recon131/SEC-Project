import { getGlobalState, setGlobalState } from "components/utils/globalState";
import cookie from "js-cookie";
import axios from "axios";
import { useEffect, useState } from "react";
import { useNavigate, Link, useParams } from "react-router-dom";

export default function Component() {
  const navigate = useNavigate();
  const token = sessionStorage.getItem("jwt-token");
  const sessionID = sessionStorage.getItem("sessionID");

  // Check user is signed in
  useEffect(() => {
    if (!token) {
      navigate("/signin");
    }
  }, []);

  // "itemID": "NULL",
  // "itemName": itemName,
  // "itemDescription": itemDescription,
  // "itemAvailable": "NULL",
  // "itemPrice": price,
  // "itemQuantity": itemQuantity,
  // "itemImage": "NULL",
  // "category": "NULL"


  // Setup variables to list new items
  const {itemName, itemDescription, itemPrice, itemQuantity, itemImage, category} = formData;
  const [error, setError] = useState("");
  const [formData, setFormData] = useState({
    itemName: "",
    price: "",
    itemCondition: "",
    itemDescription: "",
  });
  const [confirm, setConfirm] = useState(false);

  // Part of drop down menu for item condition
  let itemCategory = [
    { label: "ELECTRONICS", value: "ELECTRONICS" },
    { label: "BOOKS", value: "BOOKS" },
    { label: "CLOTHING", value: "CLOTHING" },
    {label: "GAMES", value: "GAMES"},
    {label: "FOOD", value: "FOOD"},
    {label: "OTHER", value: "OTHER"}
  ]
  let [itemCategoryDropDown, setItemCategoryDropDown] = useState("UNDEFINED")

  let handleItemConditionChange = (e) => {
    setItemCategoryDropDown(e.target.value)
  }


  function formInputs(event) {
    event.preventDefault();
    var { name, value } = event.target;
    setFormData({ ...formData, [name]: value });
  }


  async function formSubmit(event) {
    event.preventDefault();
    try {
      setFormData({...formData, itemCondition: itemCategoryDropDown});

      const data = {
        "itemID": "NULL",
        "itemName": itemName,
        "itemDescription": itemDescription,
        "itemAvailable": "NULL",
        "itemPrice": itemPrice,
        "itemQuantity": itemQuantity,
        "itemImage": "NULL",
        "category": "NULL"
      }

      // Create listing
      var res1 = await axios.post(getGlobalState("backendDomain") + "/api/authorised/catalogue/listItem?sessionID=" + sessionID, data);

      //
      navigate("/");
    } catch (resError) {
      setError(resError.response.data.error);
    }
  }

  return (
      <form
          onSubmit={formSubmit}
          className="bg-white text-black shadow-md rounded px-96 pt-6 pb-8 mb-4 flex flex-col"
      >
        <h1 className="text-3xl font-bold mb-10">Create new item listing</h1>
        <div className="mb-4">
          <label className="block text-grey-darker text-sm font-bold mb-2">
            listingTitle
          </label>
          <input
              value={itemName}
              name="listingTitle"
              onChange={formInputs}
              className="shadow appearance-none border rounded w-full py-2 px-3 text-grey-darker"
              type="text"
              placeholder="intresting title"
              required
          />
        </div>
        <div className="mb-6">
          <label className="block text-grey-darker text-sm font-bold mb-2">
          itemPrice
          </label>
          <input
              value={itemPrice}
              name="price"
              onChange={formInputs}
              className="shadow appearance-none border border-red rounded w-full py-2 px-3 text-grey-darker mb-3"
              type="text"
              placeholder="42"
              required
          />
        </div>


        <div className="mb-6">
          <label className="block text-grey-darker text-sm font-bold mb-2">
            itemCondition
          </label>
          <select onChange={handleItemConditionChange}>
            <option value="UNDEFINED"> -- Select your item's condition -- </option>
            {
              itemCategory.map((itemConditionDropDown) => <option value={itemConditionDropDown.value}>{itemConditionDropDown.label}</option>)
            }
          </select>
        </div>

        <div className="mb-6">
          <label className="block text-grey-darker text-sm font-bold mb-2">
            description
          </label>
          <input
              value={itemDescription}
              name="description"
              onChange={formInputs}
              className="shadow appearance-none border border-red rounded w-full py-2 px-3 text-grey-darker mb-3"
              type="text"
              placeholder="tell us about this item"
              required
          />
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
