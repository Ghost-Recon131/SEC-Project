package rmit.sec.webstorepmicroservice.Catalogue.services;

import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rmit.sec.webstorepmicroservice.Catalogue.customObjects.EncryptedCatalogueItem;
import rmit.sec.webstorepmicroservice.Catalogue.model.CatalogueItem;
import rmit.sec.webstorepmicroservice.Catalogue.repository.CatalogueItemRepository;
import rmit.sec.webstorepmicroservice.SessionKeyService.services.SessionKeyService;
import rmit.sec.webstorepmicroservice.utils.EncryptionUtil;
import rmit.sec.webstorepmicroservice.utils.ItemCatagory;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class CatalogueServicePublic {
    @Autowired
    private CatalogueItemRepository catalogueItemRepository;
    @Autowired
    private EncryptionUtil encryptionUtil;
    @Autowired
    private SessionKeyService sessionKeyService;

    private final Logger logger = LogManager.getLogger(this.getClass());

    // Get all items in the catalogue
    public List<EncryptedCatalogueItem> getAllItems(Long sessionID) {
        List<CatalogueItem> allItems = catalogueItemRepository.findAll();
        return encryptCatalogueItems(sessionID, allItems);
    }

    // Get all items listed by a particular seller
    public List<EncryptedCatalogueItem> getItemsBySellerID(Long sessionID, Long sellerID) {
        List<CatalogueItem> sellerItems = catalogueItemRepository.findAllBySellerID(sellerID);
        return encryptCatalogueItems(sessionID, sellerItems);
    }

    // Get items by its category
    public List<EncryptedCatalogueItem> getItemsByCategory(Long sessionID, String category) {
        List<CatalogueItem> sellerItems = catalogueItemRepository.findAllByItemCategory(ItemCatagory.valueOf(category.toUpperCase()));
        return encryptCatalogueItems(sessionID, sellerItems);
    }

    // Get items by that are available for purchase
    public List<EncryptedCatalogueItem> getAvailableItems(Long sessionID) {
        List<CatalogueItem> sellerItems = catalogueItemRepository.findAllByItemAvailable(Boolean.TRUE);

        // Lambda function to remove any items with quantity of 0
        sellerItems.removeIf(catalogueItem -> catalogueItem.getItemQuantity() == 0);

        return encryptCatalogueItems(sessionID, sellerItems);
    }

    // Convert provided list of CatalogueItems to encrypted form
    private List<EncryptedCatalogueItem> encryptCatalogueItems(Long sessionID, List<CatalogueItem> items) {
        List<EncryptedCatalogueItem> encryptedCatalogueItems = new ArrayList<>();
        try{
            // Encrypt fields of each item then add to another arraylist
            for (CatalogueItem catalogueItem : items) {
                EncryptedCatalogueItem encryptedCatalogueItem = new EncryptedCatalogueItem(
                        sessionKeyService.aesEncryptMessage(sessionID, catalogueItem.getItemID().toString()),
                        sessionKeyService.aesEncryptMessage(sessionID, catalogueItem.getSellerID().toString()),
                        sessionKeyService.aesEncryptMessage(sessionID, catalogueItem.getItemName()),
                        sessionKeyService.aesEncryptMessage(sessionID, catalogueItem.getItemDescription()),
                        sessionKeyService.aesEncryptMessage(sessionID, catalogueItem.getItemAvailable().toString()),
                        sessionKeyService.aesEncryptMessage(sessionID, catalogueItem.getItemPrice().toString()),
                        sessionKeyService.aesEncryptMessage(sessionID, catalogueItem.getItemQuantity().toString()),
                        sessionKeyService.aesEncryptMessage(sessionID, catalogueItem.getItemImage()),
                        sessionKeyService.aesEncryptMessage(sessionID, catalogueItem.getItemCategory().toString())
                );
                encryptedCatalogueItems.add(encryptedCatalogueItem);
            }
        }catch (Exception e){
            logger.error("Error returning list of all items \n");
            logger.error(e.getMessage());
        }
        return encryptedCatalogueItems;
    }

    // Returns a single item from the catalogue
    public EncryptedCatalogueItem getItemByID(Long sessionID, Long itemID) {
        List<CatalogueItem> itemList = new ArrayList<>();

        // Using an arraylist then returning the first element as we know there is only 1 item
        // This prevents rewriting 'encryptCatalogueItems' method for a single item
        itemList.add(catalogueItemRepository.getCatalogueItemByItemID(itemID));
        return encryptCatalogueItems(sessionID, itemList).get(0);
    }

}
