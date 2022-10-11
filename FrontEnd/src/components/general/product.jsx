import {addItemToCart} from "../utils/shoppingCart";

export default function Component({ item }) {
  const userID = localStorage.getItem("userID");

  // Pass items we want to add to cart
  function addToCart(e) {
    e.preventDefault();
    addItemToCart(item);
  }

  return (
    <a
      href={"/itemListing?itemID=" + item.itemID}
      className="mb-4 mr-4 max-w-[16rem] max-h-[32rem] text-sm rounded-lg bg-white hover:bg-gray-300 "
    >
      <img
        className="h-44 w-96 rounded-t-lg object-cover"
        src={item.itemImage}
      />
      <div className="mb-5 px-5 pt-5">
        <p className="h-16 font-bold tracking-tight text-gray-900 flex justify-start">
          {item.itemName}
        </p>
        <p className="mb-2 font-bold tracking-tight text-gray-900 flex justify-start">
          ${item.itemPrice}
        </p>

        {/*  Do not show the add to cart button if user is not signed in or is the owner of the item  */}
        {(userID) && (userID !== item.sellerID) ? (
            <button
                onClick={addToCart}
                className="cursor-pointer inline-flex w-full items-center justify-center rounded-lg bg-black py-2 m font-medium text-white "
            >
              Add to Cart
            </button>
            ) : (
            <button
                className="cursor-pointer inline-flex w-full items-center justify-center rounded-lg bg-black py-2 m font-medium text-white ">
              View item
            </button>
        )}
      </div>
    </a>
  );
}
