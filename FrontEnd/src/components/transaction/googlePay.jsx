import GooglePayButton from "@google-pay/button-react";
import {clientAESEncrypt} from "../security/EncryptionUtils";
import axios from "axios";
import {getGlobalState} from "../utils/globalState";
import {getItemDetails} from "../utils/getItemDetails";
import {useNavigate} from "react-router-dom";

// Google Pay code
export default function Component({ totalPrice }) {
  const token = localStorage.getItem("jwt-token");
  const sessionID = localStorage.getItem("sessionID");
  const navigate = useNavigate();

  // Set the total price
  let totalCost = "0";
  if(totalPrice){
    totalCost = totalPrice.toString();
  }

  // create the payment request object
  function getPaymentRequest() {
    // Set various details
    const paymentRequest = {
      apiVersion: 2,
      apiVersionMinor: 0,
      allowedPaymentMethods: [
        {
          type: "CARD",
          parameters: {
            allowedAuthMethods: ["PAN_ONLY", "CRYPTOGRAM_3DS"],
            allowedCardNetworks: ["MASTERCARD", "VISA"],
          },
          tokenizationSpecification: {
            type: "PAYMENT_GATEWAY",
            parameters: {
              gateway: "example",
              gatewayMerchantId: "exampleGatewayMerchantId",
            },
          },
        },
      ],
      merchantInfo: {
        merchantName: "Example Merchant"
      },
      transactionInfo: {
        totalPriceStatus: "FINAL",
        totalPriceLabel: "Total",
        totalPrice: totalCost,
        currencyCode: "AUD",
        countryCode: "AU",
      },
      shippingAddressRequired: true,
      callbackIntents: ["PAYMENT_AUTHORIZATION"],
    };
    return paymentRequest;
  }


  // This function tells the backend a transaction is successful and to record the transaction
  async function finalisePayment(){
    // Get our current cart items
    const userCart = localStorage.getItem("cart");
    const cartItems = JSON.parse(userCart);

     // Check if the user is adding a copy of an item that is already in the cart, in this case, increment the quantity
    for (let i = 0; i < cartItems.length; i++) {
      // Convert the item to JSON object then get the values
        const currentItem = cartItems[i];
        const itemID = currentItem.itemID;
        let itemQuantity = currentItem.quantity;

        // Get the rest of the item details
        const fullProductInfo = JSON.parse(await getItemDetails(currentItem.itemID));

      // Construct data to send to backend
      let data = {
        "transactionID": clientAESEncrypt("NULL"),
        "itemID": clientAESEncrypt(itemID),
        "sellerID": clientAESEncrypt(fullProductInfo.sellerID),
        "buyerID": clientAESEncrypt("NULL"),
        "totalCost": clientAESEncrypt(fullProductInfo.itemPrice),
        "transactionDate": clientAESEncrypt("NULL"),
      }

      // Send the transaction details to backend
      try{
        let response = await axios.post(getGlobalState("backendDomain") +
            "/api/authorised/transactions/saveTransaction?sessionID=" + sessionID,
            data, {headers: {Authorization: token}});
       response = response.data;

       // On successful transaction, clear the cart
       if(response === "Transaction saved successfully"){
         localStorage.removeItem("cart");
         navigate("/viewtransactions")
       }
      }catch (e){
        console.log("Exception occurred: ", e);
      }
    }
  }

  // Return the button to cart frontend
  return (
    <GooglePayButton
      environment="TEST"
      paymentRequest={getPaymentRequest()}
      onLoadPaymentData={(paymentRequest) => {
      }}
      onPaymentAuthorized={(paymentData) => {
        finalisePayment();
        // console.log("paymentData " + JSON.stringify(paymentData));
        return { transactionState: "SUCCESS" };
      }}
      existingPaymentMethodRequired="false"
      buttonColor="black"
      buttonType="buy"
    ></GooglePayButton>
  );
}