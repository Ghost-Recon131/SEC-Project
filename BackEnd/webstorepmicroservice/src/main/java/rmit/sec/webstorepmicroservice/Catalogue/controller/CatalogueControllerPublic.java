package rmit.sec.webstorepmicroservice.Catalogue.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rmit.sec.webstorepmicroservice.Catalogue.customObjects.EncryptedCatalogueItem;
import rmit.sec.webstorepmicroservice.Catalogue.services.CatalogueServicePublic;
import rmit.sec.webstorepmicroservice.SessionKeyService.services.SessionKeyService;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/catalogue")
@AllArgsConstructor
public class CatalogueControllerPublic {
    @Autowired
    private CatalogueServicePublic catalogueControllerPublic;
    @Autowired
    private SessionKeyService sessionKeyService;

    // Get single item by its ID
    @GetMapping("/item/{id}")
    public EncryptedCatalogueItem getItemByID(@RequestParam Long itemID, @RequestParam Long sessionKey) {
        return catalogueControllerPublic.getItemByID(sessionKey, itemID);
    }

    // Get all items from backend
    @GetMapping(path = "/allItems")
    public List<EncryptedCatalogueItem> viewAllItems(@RequestParam Long sessionID) {
        return catalogueControllerPublic.getAllItems(sessionKeyService.getAESKey(sessionID));
    }

    // Get all items listed by a particular seller
    @GetMapping(path = "/allItemsBySeller")
    public List<EncryptedCatalogueItem> viewItemsBySellerID(@RequestParam Long sessionID, @RequestParam Long sellerID) {
        return catalogueControllerPublic.getItemsBySellerID(sessionKeyService.getAESKey(sessionID), sellerID);
    }

    // Get items by its category
    @GetMapping(path = "/allItemsByCategory")
    public List<EncryptedCatalogueItem> viewItemsByCategory(@RequestParam Long sessionID, @RequestParam String category) {
        return catalogueControllerPublic.getItemsByCategory(sessionKeyService.getAESKey(sessionID), category);
    }

    // Get all available items
    @GetMapping(path = "/allAvailableItems")
    public List<EncryptedCatalogueItem> viewAllAvailableItems(@RequestParam Long sessionID) {
        return catalogueControllerPublic.getAvailableItems(sessionKeyService.getAESKey(sessionID));
    }

}
