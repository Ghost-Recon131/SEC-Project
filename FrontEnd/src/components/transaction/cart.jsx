import { useNavigate, Link } from "react-router-dom";
import { useEffect, useState } from "react";
import { getItemDetails } from "../utils/getItemDetails";
import { removeItemFromCart, removeItemsFromCart } from "../utils/shoppingCart";
import { Dialog, Transition } from '@headlessui/react'
import { Fragment } from 'react'
import { XMarkIcon } from '@heroicons/react/24/outline'
import GooglePay from './googlePay'


export default function Example() {
  const navigate = useNavigate();
  const token = localStorage.getItem("jwt-token");
  const sessionID = localStorage.getItem("sessionID");
  const [totalCost, setTotalCost] = useState(null);
  let products = {};
  const [itemArray, setItemArray] = useState([]);

  // Check user is signed in
  useEffect(() => {
    if (!token) {
      navigate("/signin");
    } else {
      getProductDetails()
    }
  }, []);

  async function getProductDetails() {
    // Get the user's cart info
    const userCart = localStorage.getItem("cart");
    products = JSON.parse(userCart);


    // using for loop here as for each loop do not support 'await'
    // Get arraylist of the full product details
    let productArray = []
    let tmpCost = 0
    for (let i = 0; i < products.length; i++) {
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
  function continueShopping() {
    navigate("/");
  }

  // Removes an item entirely from the cart
  async function removeItem(itemID) {
    const userCart = localStorage.getItem("cart");
    products = JSON.parse(userCart);
    removeItemsFromCart(itemID);
    await getProductDetails();
  }

  const [open, setOpen] = useState(true)

  return (
    <Transition.Root show={open} as={Fragment}>
      <Dialog as="div" className="relative z-10" onClose={setOpen}>
        <Transition.Child
          as={Fragment}
          enter="ease-in-out duration-500"
          enterFrom="opacity-0"
          enterTo="opacity-100"
          leave="ease-in-out duration-500"
          leaveFrom="opacity-100"
          leaveTo="opacity-0"
        >
          <div className="fixed inset-0 bg-gray-500 bg-opacity-75 transition-opacity" />
        </Transition.Child>

        <div className="fixed inset-0 overflow-hidden">
          <div className="absolute inset-0 overflow-hidden">
            <div className="pointer-events-none fixed inset-y-0 right-0 flex max-w-full pl-10">
              <Transition.Child
                as={Fragment}
                enter="transform transition ease-in-out duration-500 sm:duration-700"
                enterFrom="translate-x-full"
                enterTo="translate-x-0"
                leave="transform transition ease-in-out duration-500 sm:duration-700"
                leaveFrom="translate-x-0"
                leaveTo="translate-x-full"
              >
                <Dialog.Panel className="pointer-events-auto flex justify-center">
                  <div className="flex h-full flex-col bg-white shadow-xl w-[1200px] rounded-lg">
                    <div className="flex-1 overflow-y-auto py-6 px-4 sm:px-6">
                      <div className="flex items-start justify-between">
                        <Dialog.Title className="text-lg font-medium text-gray-900">Shopping cart</Dialog.Title>
                        <div className="ml-3 flex h-7 items-center">
                          <button
                            type="button"
                            className="-m-2 p-2 text-gray-400 hover:text-gray-500"
                            onClick={() => setOpen(false)}
                          >
                            <span className="sr-only">Close panel</span>
                            <XMarkIcon className="h-6 w-6" aria-hidden="true" />
                          </button>
                        </div>
                      </div>

                      <div className="mt-8">
                        <div className="flow-root">
                          <ul role="list" className="-my-6 divide-y divide-gray-200">
                            {itemArray.map((product) => (
                              <li key={product.id} className="flex py-6">
                                <div className="h-24 w-24 flex-shrink-0 overflow-hidden rounded-md border border-gray-200">
                                  <img
                                    src={product.itemImage}
                                    alt="Failed to load image"
                                    className="h-full w-full object-cover object-center"
                                  />
                                </div>

                                <div className="ml-4 flex flex-1 flex-col">
                                  <div>
                                    <div className="flex justify-between text-base font-medium text-gray-900">
                                      <h3>
                                        <a href={product.href}>{product.itemName}</a>
                                      </h3>
                                      <p className="ml-4">${product.itemPrice} per item</p>
                                    </div>
                                    <p className="mt-1 text-sm text-gray-500">{product.itemCategory}</p>
                                  </div>
                                  <div className="flex flex-1 items-end justify-between text-sm">
                                    <p className="text-gray-500">Quantity {product.cartQuantity}</p>

                                    <div className="flex">
                                      <button
                                        type="button"
                                        className="font-medium text-indigo-600 hover:text-indigo-500"
                                        onClick={() => removeItem(product.itemID)}
                                      >
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
                      <p className="mt-0.5 text-sm text-gray-500">Shipping and taxes calculated at checkout.</p>
                      <div className="mt-6">
                        <a
                          className="flex items-center justify-center rounded-md border border-transparent bg-white px-6 py-3 text-base font-medium text-white shadow-sm hover:bg-white">
                          <GooglePay totalPrice={totalCost}/>
                        </a>
                      </div>
                      <div className="mt-6 flex justify-center text-center text-sm text-gray-500">
                        <p>
                          or {" "}
                          <button
                            type="button"
                            className="font-medium text-indigo-600 hover:text-indigo-500"
                            onClick={() => continueShopping()}>
                            Continue Shopping
                            <span aria-hidden="true"> &rarr;</span>
                          </button>
                        </p>
                      </div>
                    </div>
                  </div>
                </Dialog.Panel>
              </Transition.Child>
            </div>
          </div>
        </div>
      </Dialog>
    </Transition.Root>
  )
}
