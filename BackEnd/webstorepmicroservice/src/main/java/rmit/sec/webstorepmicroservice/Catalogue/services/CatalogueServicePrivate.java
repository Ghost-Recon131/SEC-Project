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

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class CatalogueServicePrivate {
    @Autowired
    private CatalogueItemRepository catalogueItemRepository;
    @Autowired
    private EncryptionUtil encryptionUtil;

    private final Logger logger = LogManager.getLogger(this.getClass());

    // Get all items in the catalogue
    private List<EncryptedCatalogueItem> getAllItems(String sessionKey) {
        List<CatalogueItem> allItems = catalogueItemRepository.findAll();
        return encryptCatalogueItems(sessionKey, allItems);
    }

    // TODO: getCatalogueItemsBySellerID

    // TODO: getCatalogueItemsByItemCategory

    // TODO: getCatalogueItemsByItemAvailable


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

}
