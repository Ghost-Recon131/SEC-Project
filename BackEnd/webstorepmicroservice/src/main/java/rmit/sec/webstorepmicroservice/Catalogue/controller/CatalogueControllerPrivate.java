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
import rmit.sec.webstorepmicroservice.utils.JWTUtil;
import rmit.sec.webstorepmicroservice.utils.TypeConvertUtil;

import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
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

    // Endpoint to List item
    @PostMapping("/listItem")
    public String listItem(HttpServletRequest request, @RequestParam Long sessionID, @RequestBody EncryptedEditItemRequest itemRequest) {
        Long sellerID = jwtUtil.getUserIdByJWT(request).getId();

        // Decrypt the item request from frontend & convert types as needed
        Double itemPrice = typeConvertUtil.convertToDouble(sessionKeyService.aesDecryptMessage(sessionID, itemRequest.getItemPrice()));
        Integer itemQuantity = typeConvertUtil.convertToInt(sessionKeyService.aesDecryptMessage(sessionID, itemRequest.getItemQuantity()));

        // Pass the decrypted value to 'catalogueServicePrivate' to further process the request
        ItemListingRequest decryptedItemRequest = new ItemListingRequest(
                sessionKeyService.aesDecryptMessage(sessionID, itemRequest.getItemName()),
                sessionKeyService.aesDecryptMessage(sessionID, itemRequest.getItemDescription()),
                itemPrice,
                itemQuantity,
                sessionKeyService.aesDecryptMessage(sessionID, itemRequest.getItemImage()),
                sessionKeyService.aesDecryptMessage(sessionID, itemRequest.getItemCategory())
        );
        return catalogueServicePrivate.listItem(sellerID, decryptedItemRequest, sessionID);
    }

    // Endpoint to edit a listing the user owns
    @PostMapping("/editItem")
    public String editItem(HttpServletRequest request, @RequestParam Long sessionID, @RequestBody EncryptedEditItemRequest itemRequest) {
        String result = "";

        Long sellerID = jwtUtil.getUserIdByJWT(request).getId();

        // Decrypt the item request from frontend & convert types as needed
        Long itemID =  typeConvertUtil.convertToLong(sessionKeyService.aesDecryptMessage(sessionID, itemRequest.getItemID()));
        Double itemPrice = typeConvertUtil.convertToDouble(sessionKeyService.aesDecryptMessage(sessionID, itemRequest.getItemPrice()));
        Integer itemQuantity = typeConvertUtil.convertToInt(sessionKeyService.aesDecryptMessage(sessionID, itemRequest.getItemQuantity()));
        Boolean itemAvailable = typeConvertUtil.convertToBoolean(sessionKeyService.aesDecryptMessage(sessionID, itemRequest.getItemAvailable()));

        // Check that item availability is converted properly
        if(itemAvailable != null){
            // Pass the decrypted value to 'catalogueServicePrivate' to further process the request
            EditItemListingRequest decryptedRequest = new EditItemListingRequest(
                    itemID,
                    sessionKeyService.aesDecryptMessage(sessionID, itemRequest.getItemName()),
                    sessionKeyService.aesDecryptMessage(sessionID, itemRequest.getItemDescription()),
                    itemAvailable,
                    itemPrice,
                    itemQuantity,
                    sessionKeyService.aesDecryptMessage(sessionID, itemRequest.getItemImage()),
                    sessionKeyService.aesDecryptMessage(sessionID, itemRequest.getItemCategory())
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
