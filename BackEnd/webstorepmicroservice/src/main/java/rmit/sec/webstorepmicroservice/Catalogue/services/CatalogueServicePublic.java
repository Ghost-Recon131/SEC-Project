package rmit.sec.webstorepmicroservice.Catalogue.services;

import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rmit.sec.webstorepmicroservice.Catalogue.customObjects.EncryptedCatalogueItem;
import rmit.sec.webstorepmicroservice.Catalogue.model.CatalogueItem;
import rmit.sec.webstorepmicroservice.Catalogue.repository.CatalogueItemRepository;
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

    private final Logger logger = LogManager.getLogger(this.getClass());

    // Get all items in the catalogue
    public List<EncryptedCatalogueItem> getAllItems(String sessionKey) {
        List<CatalogueItem> allItems = catalogueItemRepository.findAll();
        return encryptCatalogueItems(sessionKey, allItems);
    }

    // Get all items listed by a particular seller
    public List<EncryptedCatalogueItem> getItemsBySellerID(String sessionKey, Long sellerID) {
        List<CatalogueItem> sellerItems = catalogueItemRepository.findAllBySellerID(sellerID);
        return encryptCatalogueItems(sessionKey, sellerItems);
    }

    // Get items by its category
    public List<EncryptedCatalogueItem> getItemsByCategory(String sessionKey, String category) {
        List<CatalogueItem> sellerItems = catalogueItemRepository.findAllByItemCategory(ItemCatagory.valueOf(category.toUpperCase()));
        return encryptCatalogueItems(sessionKey, sellerItems);
    }

    // Get items by that are available for purchase
    public List<EncryptedCatalogueItem> getAvailableItems(String sessionKey) {
        List<CatalogueItem> sellerItems = catalogueItemRepository.findAllByItemAvailable(Boolean.TRUE);

        // Lambda function to remove any items with quantity of 0
        sellerItems.removeIf(catalogueItem -> catalogueItem.getItemQuantity() == 0);

        return encryptCatalogueItems(sessionKey, sellerItems);
    }

    // Convert provided list of CatalogueItems to encrypted form
    private List<EncryptedCatalogueItem> encryptCatalogueItems(String sessionKey, List<CatalogueItem> items) {
        List<EncryptedCatalogueItem> encryptedCatalogueItems = new ArrayList<>();
        try{
            // Encrypt fields of each item then add to another arraylist
            for (CatalogueItem catalogueItem : items) {
                EncryptedCatalogueItem encryptedCatalogueItem = new EncryptedCatalogueItem(
                        encryptionUtil.serverAESEncrypt(sessionKey, catalogueItem.getItemID().toString()),
                        encryptionUtil.serverAESEncrypt(sessionKey, catalogueItem.getSellerID().toString()),
                        encryptionUtil.serverAESEncrypt(sessionKey, catalogueItem.getItemName()),
                        encryptionUtil.serverAESEncrypt(sessionKey, catalogueItem.getItemDescription()),
                        encryptionUtil.serverAESEncrypt(sessionKey, catalogueItem.getItemAvailable().toString()),
                        encryptionUtil.serverAESEncrypt(sessionKey, catalogueItem.getItemPrice().toString()),
                        encryptionUtil.serverAESEncrypt(sessionKey, catalogueItem.getItemQuantity().toString()),
                        encryptionUtil.serverAESEncrypt(sessionKey, catalogueItem.getItemImage()),
                        encryptionUtil.serverAESEncrypt(sessionKey, catalogueItem.getItemCategory().toString())
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
    public EncryptedCatalogueItem getItemByID(Long sessionKey, Long itemID) {
        List<CatalogueItem> itemList = new ArrayList<>();

        // Using an arraylist then returning the first element as we know there is only 1 item
        // This prevents rewriting 'encryptCatalogueItems' method for a single item
        itemList.add(catalogueItemRepository.getCatalogueItemByItemID(itemID));
        return encryptCatalogueItems(sessionKey.toString(), itemList).get(0);
    }

}
