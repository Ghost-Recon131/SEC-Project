export default function Component({ item }) {
  function addToCart(e) {
    e.preventDefault();
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
        <button
          onClick={addToCart}
          className="cursor-pointer inline-flex w-full items-center justify-center rounded-lg bg-black py-2 m font-medium text-white "
        >
          Add to Cart
        </button>
      </div>
    </a>
  );
}
