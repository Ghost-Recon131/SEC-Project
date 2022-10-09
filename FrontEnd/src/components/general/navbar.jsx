import cookie from "js-cookie";
import { useNavigate, Link } from "react-router-dom";

export default function Component() {
  const navigate = useNavigate();

  function accountList(event) {
    event.preventDefault();
    if (event.target.value === "signOut") {
      sessionStorage.removeItem("jwt-token");
      sessionStorage.removeItem("user");
      navigate("/");
    } else if (event.target.value === "newListing") {
      navigate("/newlisting");
    }else if (event.target.value === "viewAccountInfo") {
      navigate("/viewAccountInfo");
    }else if (event.target.value === "viewtransactions") {
      navigate("/viewtransactions");
    }

  }

  // Convert from string to object
  if (sessionStorage.getItem("user")) {
    var user = sessionStorage.getItem("user")
    console.log("View user cookie: " + user);
  }
  return (
    <nav className="font-sans flex flex-col text-center sm:flex-row sm:text-left sm:justify-between py-4 px-6  shadow sm:items-baseline w-full">
      <div className="mb-2 sm:mb-0">
        <Link
          to="/"
          className="text-2xl no-underline text-grey-darkest hover:text-blue-dark"
        >
          Home
        </Link>
      </div>
      <div>



        <div>
          {user ? (
              <div className="rounded hover:font-medium hover:text-gray-400 md:mx-2">
              </div>
          ) : (
              <Link
                  to="/register"
                  className="text-lg no-underline text-grey-darkest hover:text-blue-dark ml-2"
              >
                Register
              </Link>
          )}
        </div>


        <div>
          {user? (
              <div className="rounded hover:font-medium hover:text-gray-400 md:mx-2">
              </div>
          ) : (
              <Link
                  to="/forgotpassword"
                  className="text-lg no-underline text-grey-darkest hover:text-blue-dark ml-2"
              >
                Forgot Password
              </Link>
          )}
        </div>


        {user ? (
          <div className="rounded hover:font-medium hover:text-gray-400 md:mx-2">
            <select
              className="custom-select text-capitalize bg-black"
              onChange={accountList}
              value=""
            >
              <option hidden>{user}</option>
              <option value="newListing">New Listing</option>
              <option value="viewAccountInfo">View Account info</option>
              <option value="viewtransactions">View purchases</option>
              <option value="signOut">Sign Out</option>
            </select>
          </div>
        ) : (
          <Link
            to="/signin"
            className="text-lg no-underline text-grey-darkest hover:text-blue-dark ml-2"
          >
            Sign in
          </Link>
        )}
      </div>

    </nav>
  );
}
