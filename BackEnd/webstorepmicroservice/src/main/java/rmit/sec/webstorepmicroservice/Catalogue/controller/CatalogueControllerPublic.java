package rmit.sec.webstorepmicroservice.Catalogue.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rmit.sec.webstorepmicroservice.Catalogue.customObjects.EncryptedCatalogueItem;
import rmit.sec.webstorepmicroservice.Catalogue.customObjects.EncryptedItemViewRequest;
import rmit.sec.webstorepmicroservice.Catalogue.services.CatalogueServicePublic;
import rmit.sec.webstorepmicroservice.SessionKeyService.services.SessionKeyService;
import rmit.sec.webstorepmicroservice.utils.EncryptionUtil;
import rmit.sec.webstorepmicroservice.utils.TypeConvertUtil;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/catalogue")
@AllArgsConstructor
public class CatalogueControllerPublic {
    @Autowired
    private CatalogueServicePublic catalogueServicePublic;
    @Autowired
    private EncryptionUtil encryptionUtil;
    @Autowired
    private SessionKeyService sessionKeyService;
    @Autowired
    private TypeConvertUtil typeConvertUtil;

    // Get single item by its ID
    @GetMapping("/viewItem")
    public EncryptedCatalogueItem getItemByID(@RequestParam Long sessionID, @RequestBody EncryptedItemViewRequest request) {
        // Get the session key then decrypt the request
        String sessionKey = sessionKeyService.getAESKey(sessionID);
        Long itemID = typeConvertUtil.convertToLong(encryptionUtil.serverAESDecrypt(sessionKey, request.getItemID()));
        return catalogueServicePublic.getItemByID(sessionID, itemID);
    }

    // Get all items from backend
    @GetMapping(path = "/allItems")
    public List<EncryptedCatalogueItem> viewAllItems(@RequestParam Long sessionID) {
        return catalogueServicePublic.getAllItems(sessionKeyService.getAESKey(sessionID));
    }

    // Get all items listed by a particular seller
    @GetMapping(path = "/allItemsBySeller")
    public List<EncryptedCatalogueItem> viewItemsBySellerID(@RequestParam Long sessionID, @RequestBody EncryptedItemViewRequest request) {
        String sessionKey = sessionKeyService.getAESKey(sessionID);
        Long sellerID = typeConvertUtil.convertToLong(encryptionUtil.serverAESDecrypt(sessionKey, request.getSellerID()));
        return catalogueServicePublic.getItemsBySellerID(sessionKeyService.getAESKey(sessionID), sellerID);
    }

    // Get items by its category
    @GetMapping(path = "/allItemsByCategory")
    public List<EncryptedCatalogueItem> viewItemsByCategory(@RequestParam Long sessionID, @RequestBody EncryptedItemViewRequest request) {
        String sessionKey = sessionKeyService.getAESKey(sessionID);
        return catalogueServicePublic.getItemsByCategory(sessionKey, encryptionUtil.serverAESDecrypt(sessionKey, request.getCategory()));
    }

    // Get all available items
    @GetMapping(path = "/allAvailableItems")
    public List<EncryptedCatalogueItem> viewAllAvailableItems(@RequestParam Long sessionID) {
        return catalogueServicePublic.getAvailableItems(sessionKeyService.getAESKey(sessionID));
    }

}
