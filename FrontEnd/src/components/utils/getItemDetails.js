import {clientAESDecrypt, clientAESEncrypt} from "../security/EncryptionUtils";
import axios from "axios";
import {getGlobalState} from "./globalState";

export async function getItemDetails(itemID) {
    const sessionID = localStorage.getItem("sessionID");

    const request = {
        itemID: clientAESEncrypt(itemID),
        sellerID: "",
        category: "",
    };

    let item = await axios.post(getGlobalState("backendDomain") + "/api/catalogue/viewItem?sessionID=" + sessionID, request);
    // console.log("ITEM: ", JSON.stringify(item));
    item = item.data;

    const decryptedItem = {
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
    // console.log("decryptedItem: ", JSON.stringify(decryptedItem));

    return JSON.stringify(decryptedItem);
}