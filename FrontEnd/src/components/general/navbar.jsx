import { useNavigate, Link } from "react-router-dom";

export default function Component() {
  const navigate = useNavigate();

  function accountList(event) {
    event.preventDefault();

    // Navigate to different sections of the site
    if (event.target.value === "signOut") {
      // Clear our session storage on sign out
      localStorage.removeItem("jwt-token");
      localStorage.removeItem("user");
      localStorage.removeItem("userID");
      navigate("/");
    } else if (event.target.value === "newListing") {
      navigate("/newlisting");
    } else if (event.target.value === "viewAccountInfo") {
      navigate("/viewAccountInfo");
    } else if (event.target.value === "viewtransactions") {
      navigate("/viewtransactions");
    }
  }

  // Convert from string to object
  if (localStorage.getItem("user")) {
    var user = localStorage.getItem("user");
  }
  return (
    <nav className="font-sans flex flex-col text-center sm:flex-row sm:justify-between py-4 sm:items-baseline w-full">
      <a
        href="/"
        className="mb-2 sm:mb-0 text-2xl no-underline text-grey-darkest hover:text-blue-dark"
      >
        SEC Project
      </a>
      <div className="">
        {user ? (
          <div className="flex justify-end rounded hover:font-medium md:mx-2">
            <a href="/cart">
              <svg
                className="hover:text-gray-400 w-6 h-6"
                xmlns="http://www.w3.org/2000/svg"
                fill="none"
                viewBox="0 0 24 24"
                strokeWidth={1.5}
                stroke="currentColor"
              >
                <path
                  strokeLinecap="round"
                  strokeLinejoin="round"
                  d="M2.25 3h1.386c.51 0 .955.343 1.087.835l.383 1.437M7.5 14.25a3 3 0 00-3 3h15.75m-12.75-3h11.218c1.121-2.3 2.1-4.684 2.924-7.138a60.114 60.114 0 00-16.536-1.84M7.5 14.25L5.106 5.272M6 20.25a.75.75 0 11-1.5 0 .75.75 0 011.5 0zm12.75 0a.75.75 0 11-1.5 0 .75.75 0 011.5 0z"
                />
              </svg>
            </a>
            <select
              className="custom-select text-capitalize bg-black hover:text-gray-400 ml-4"
              onChange={accountList}
              value=""
            >
              <option hidden>{user}</option>
              <option value="newListing">New Listing</option>
              <option value="viewAccountInfo">Account info</option>
              <option value="viewtransactions">Purchase History</option>
              <option value="signOut">Sign Out</option>
            </select>
          </div>
        ) : (
          <div>
            <a
              href="/register"
              className="text-lg no-underline text-grey-darkest hover:text-blue-dark ml-2"
            >
              Register
            </a>
            <a
              href="/signin"
              className="text-lg no-underline text-grey-darkest hover:text-blue-dark ml-2"
            >
              Sign in
            </a>
          </div>
        )}
      </div>
    </nav>
  );
}
