package rmit.sec.webstorepmicroservice.Catalogue.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rmit.sec.webstorepmicroservice.Catalogue.customObjects.EncryptedCatalogueItem;
import rmit.sec.webstorepmicroservice.Catalogue.customObjects.EncryptedEditItemRequest;
import rmit.sec.webstorepmicroservice.Catalogue.customObjects.EncryptedItemViewRequest;
import rmit.sec.webstorepmicroservice.Catalogue.requests.EditItemListingRequest;
import rmit.sec.webstorepmicroservice.Catalogue.requests.ItemListingRequest;
import rmit.sec.webstorepmicroservice.Catalogue.services.CatalogueServicePrivate;
import rmit.sec.webstorepmicroservice.SessionKeyService.services.SessionKeyService;
import rmit.sec.webstorepmicroservice.utils.EncryptionUtil;
import rmit.sec.webstorepmicroservice.utils.JWTUtil;
import rmit.sec.webstorepmicroservice.utils.TypeConvertUtil;

import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/authorised/catalogue")
@AllArgsConstructor
public class CatalogueControllerPrivate {

    @Autowired
    private CatalogueServicePrivate catalogueServicePrivate;
    @Autowired
    private SessionKeyService sessionKeyService;
    @Autowired
    private JWTUtil jwtUtil;
    @Autowired
    private TypeConvertUtil typeConvertUtil;
    @Autowired
    private EncryptionUtil encryptionUtil;

    // Endpoint to List item
    @PostMapping("/listItem")
    public String listItem(HttpServletRequest request, @RequestParam Long sessionID, @RequestBody EncryptedEditItemRequest itemRequest) {
        Long sellerID = jwtUtil.getUserIdByJWT(request).getId();

        // Decrypt the item request from frontend & convert types as needed
        String sessionKey = sessionKeyService.getAESKey(sessionID);
        Double itemPrice = typeConvertUtil.convertToDouble(encryptionUtil.serverAESDecrypt(sessionKey, itemRequest.getItemPrice()));
        Integer itemQuantity = typeConvertUtil.convertToInt(encryptionUtil.serverAESDecrypt(sessionKey, itemRequest.getItemQuantity()));

        // Pass the decrypted value to 'catalogueServicePrivate' to further process the request
        ItemListingRequest decryptedItemRequest = new ItemListingRequest(
                encryptionUtil.serverAESDecrypt(sessionKey, itemRequest.getItemName()),
                encryptionUtil.serverAESDecrypt(sessionKey, itemRequest.getItemDescription()),
                itemPrice,
                itemQuantity,
                encryptionUtil.serverAESDecrypt(sessionKey, itemRequest.getItemImage()),
                encryptionUtil.serverAESDecrypt(sessionKey, itemRequest.getItemCategory())
        );
        return catalogueServicePrivate.listItem(sellerID, decryptedItemRequest, sessionID);
    }

    // Endpoint to edit a listing the user owns
    @PostMapping("/editItem")
    public String editItem(HttpServletRequest request, @RequestParam Long sessionID, @RequestBody EncryptedEditItemRequest itemRequest) {
        String result = "";

        String sessionKey = sessionKeyService.getAESKey(sessionID);
        Long sellerID = jwtUtil.getUserIdByJWT(request).getId();

        // Decrypt the item request from frontend & convert types as needed
        Long itemID =  typeConvertUtil.convertToLong(encryptionUtil.serverAESDecrypt(sessionKey, itemRequest.getItemID()));
        Double itemPrice = typeConvertUtil.convertToDouble(encryptionUtil.serverAESDecrypt(sessionKey, itemRequest.getItemPrice()));
        Integer itemQuantity = typeConvertUtil.convertToInt(encryptionUtil.serverAESDecrypt(sessionKey, itemRequest.getItemQuantity()));
        Boolean itemAvailable = typeConvertUtil.convertToBoolean(encryptionUtil.serverAESDecrypt(sessionKey, itemRequest.getItemAvailable()));

        // Check that item availability is converted properly
        if(itemAvailable != null){
            // Pass the decrypted value to 'catalogueServicePrivate' to further process the request
            EditItemListingRequest decryptedRequest = new EditItemListingRequest(
                    itemID,
                    encryptionUtil.serverAESDecrypt(sessionKey, itemRequest.getItemName()),
                    encryptionUtil.serverAESDecrypt(sessionKey, itemRequest.getItemDescription()),
                    itemAvailable,
                    itemPrice,
                    itemQuantity,
                    encryptionUtil.serverAESDecrypt(sessionKey, itemRequest.getItemImage()),
                    encryptionUtil.serverAESDecrypt(sessionKey, itemRequest.getItemCategory())
            );
            result = catalogueServicePrivate.editItem(sellerID, decryptedRequest, sessionID);
        }else{
            result = "Failed to convert itemAvailable";
        }
        return result;
    }

    // Endpoint for user to mark their item as no longer available
    @PostMapping("/endListing")
    public String endListing(HttpServletRequest request, @RequestParam Long sessionID, @RequestBody EncryptedItemViewRequest editRequest) {
        Long sellerID = jwtUtil.getUserIdByJWT(request).getId();
        Long itemID =  typeConvertUtil.convertToLong(sessionKeyService.aesDecryptMessage(sessionID, editRequest.getItemID()));
        return catalogueServicePrivate.disableItem(sellerID, itemID, sessionID);
    }

}
