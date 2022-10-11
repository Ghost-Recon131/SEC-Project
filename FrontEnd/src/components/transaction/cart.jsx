import axios from "axios";
import { useNavigate, Link } from "react-router-dom";
import { useEffect, useState } from "react";
import {clientAESDecrypt, clientAESEncrypt,} from "components/security/EncryptionUtils";
import { getGlobalState } from "components/utils/globalState";
import { XMarkIcon } from "@heroicons/react/24/outline";
import {getItemDetails} from "../utils/getItemDetails";
import {removeItemFromCart} from "../utils/shoppingCart";

export default function Example() {
  const navigate = useNavigate();
  const token = localStorage.getItem("jwt-token");
  const sessionID = localStorage.getItem("sessionID");
  const [totalCost, setTotalCost] = useState(null);
  let products = {};
  const [itemArray, setItemArray] = useState([]);

  // Check user is signed in
  useEffect(() => {
    if(!token) {
      navigate("/signin");
    }else{
      getProductDetails()
    }
  }, []);

  async function getProductDetails(){
    // Get the user's cart info
    const userCart = localStorage.getItem("cart");
    products = JSON.parse(userCart);


    // using for loop here as for each loop do not support 'await'
    // Get arraylist of the full product details
    let productArray = []
    let tmpCost = 0
    for(let i= 0; i < products.length; i++){
      const item = products[i];
      const product = JSON.parse(await getItemDetails(item.itemID));

      // Setup new object with the product details and the quantity
      const productWithQuantity = {
        itemID: product.itemID,
        sellerID: product.sellerID,
        itemName: product.itemName,
        itemDescription: product.itemDescription,
        itemAvailable: product.itemAvailable,
        itemPrice: product.itemPrice,
        itemQuantity: product.itemQuantity,
        itemImage: product.itemImage,
        itemCategory: product.itemCategory,
        cartQuantity: products[i].quantity
      }
      // Add to total cost save to array
      tmpCost = tmpCost + product.itemPrice * products[i].quantity;
      productArray.push(productWithQuantity);
    }
    // Save the array and total cost to the proper react hooks
    setTotalCost(tmpCost)
    setItemArray(productArray);
  }

  // Takes user back to home page
  function continueShopping(){
    navigate("/");
  }

  // Removes an item entirely from the cart
  function removeItem(itemID){
    removeItemFromCart(itemID);
  }

  return (
      <div className="pointer-events-none flex justify-center">
        <div className="flex h-full flex-col bg-white shadow-xl w-[1200px] rounded-lg">
          <div className="flex-1 overflow-y-auto py-6 px-4 sm:px-6">
            <div className="flex items-start justify-between">
              <h1 className="text-lg font-medium text-gray-900">Shopping cart</h1>
            </div>
            <div className="mt-8">
              <div className="flow-root">
                <ul role="list" className="-my-6 divide-y divide-gray-200">
                  {itemArray.map((item) => (
                      <li key={item.itemID} className="flex py-6">
                        <div className="h-24 w-24 flex-shrink-0 overflow-hidden rounded-md border border-gray-200">
                          <img
                              src={item.itemImage}
                              alt="Image failed to load"
                              className="h-full w-full object-cover object-center"
                          />
                        </div>
                        <div className="ml-4 flex flex-1 flex-col">
                          <div>
                            <div className="flex justify-between text-base font-medium text-gray-900">
                              <h3>
                                <a href={item.href}>{item.itemName}</a>
                              </h3>
                              <p className="ml-4">${item.itemPrice} per item</p>
                            </div>
                            <p className="mt-1 text-sm text-gray-500">
                              {item.color}
                            </p>
                          </div>
                          <div className="flex flex-1 items-end justify-between text-sm">
                            <p className="text-gray-500">Quantity: {item.cartQuantity}</p>
                            <div className="flex">
                              <button
                                  onClick={removeItem(item.itemID)}
                                  type="button"
                                  className="font-medium text-indigo-600 hover:text-indigo-500">
                                Remove
                              </button>
                            </div>
                          </div>
                        </div>
                      </li>
                  ))}
                </ul>
              </div>
            </div>
          </div>

          <div className="border-t border-gray-200 py-6 px-4 sm:px-6">
            <div className="flex justify-between text-base font-medium text-gray-900">
              <p>Subtotal</p>
              <p>${totalCost}</p>
            </div>
            <p className="mt-0.5 text-sm text-gray-500">
              Shipping and taxes calculated at checkout.
            </p>
            <div className="mt-6">
              <a
                  href="#"
                  className="flex items-center justify-center rounded-md border border-transparent bg-indigo-600 px-6 py-3 text-base font-medium text-white shadow-sm hover:bg-indigo-700">
                Checkout
              </a>
            </div>
            <div className="mt-6 flex justify-center text-center text-sm text-gray-500">
              <p>
                or {"  "}
                <button
                    type="button"
                    className="font-medium text-indigo-600 hover:text-indigo-500"
                    onClick={continueShopping}>
                  Continue Shopping
                </button>
              </p>
            </div>
          </div>
        </div>
      </div>
  );
}
