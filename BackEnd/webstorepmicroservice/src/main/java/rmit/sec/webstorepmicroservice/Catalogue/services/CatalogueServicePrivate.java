package rmit.sec.webstorepmicroservice.Catalogue.services;

import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rmit.sec.webstorepmicroservice.Catalogue.model.CatalogueItem;
import rmit.sec.webstorepmicroservice.Catalogue.repository.CatalogueItemRepository;
import rmit.sec.webstorepmicroservice.Catalogue.requests.EditItemListingRequest;
import rmit.sec.webstorepmicroservice.Catalogue.requests.ItemListingRequest;
import rmit.sec.webstorepmicroservice.SessionKeyService.services.SessionKeyService;
import rmit.sec.webstorepmicroservice.utils.ItemCatagory;

import java.util.Objects;


@Service
@AllArgsConstructor
public class CatalogueServicePrivate {
    @Autowired
    private CatalogueItemRepository catalogueItemRepository;
    @Autowired
    private SessionKeyService sessionKeyService;
    private final Logger logger = LogManager.getLogger(this.getClass());

    // List new item
    public String listItem(Long sellerID, ItemListingRequest request, Long sessionID) {
        String result = "";
        CatalogueItem newItem = null;
        try{
            newItem = new CatalogueItem(
                    sellerID,
                    request.getItemName(),
                    request.getItemDescription(),
                    Boolean.FALSE,
                    request.getItemPrice(),
                    request.getItemQuantity(),
                    null,
                    ItemCatagory.valueOf(request.getItemCategory().toUpperCase())
            );
            // Set item as available only if the specified quantity is greater than 0 or "-1" which denotes unlimited quantity
            if (request.getItemQuantity() == -1 || request.getItemQuantity() > 0) {
                newItem.setItemAvailable(Boolean.TRUE);
            }
            catalogueItemRepository.save(newItem);
            result = "Successfully listed item";
        }catch (Exception e){
            result = "Failed to listed item";
            logger.warn("Error listing new item");
            logger.error(e.getMessage());
        }
        return sessionKeyService.aesEncryptMessage(sessionID, result);
    }

    // Allow user to edit item fields
    public String editItem(Long sellerID, EditItemListingRequest request, Long sessionID) {
        String result = "";
        CatalogueItem toEdit = null;
        boolean continueProcess = false;
        try{
            toEdit = catalogueItemRepository.getCatalogueItemByItemID(request.getItemID());
            continueProcess = true;
        }catch (Exception e){
            result = "Failed to edit item listing";
            logger.error(e.getMessage());
        }

        // Verify the request to edit comes from the owner of a listing
        if(continueProcess && Objects.equals(toEdit.getSellerID(), sellerID)){
            // Only update fields the user changed
            try{
                if(!request.getItemName().isEmpty()){
                    toEdit.setItemName(request.getItemName());
                }
                if(!request.getItemDescription().isEmpty()){
                    toEdit.setItemDescription(request.getItemDescription());
                }
                if(request.getItemPrice() != null && request.getItemPrice() > 0){
                    toEdit.setItemPrice(request.getItemPrice());
                }
                if(request.getItemQuantity() != null && request.getItemQuantity() >= 0){
                    toEdit.setItemQuantity(request.getItemQuantity());
                }
                if(request.getItemCategory() != null){
                    toEdit.setItemCategory(ItemCatagory.valueOf(request.getItemCategory().toUpperCase()));
                }
                if(request.getItemImage() != null){
                    toEdit.setItemImage(request.getItemImage());
                }
                if(request.getItemAvailable() != null){
                    toEdit.setItemAvailable(request.getItemAvailable());
                }
                if(toEdit.getItemQuantity() == 0){
                    toEdit.setItemAvailable(Boolean.FALSE);
                }
                catalogueItemRepository.save(toEdit);
                result = "Successfully edited item listing";
            }catch (Exception e){
                result = "Failed to edit item listing";
                logger.error(e.getMessage());
            }
        }else if(continueProcess && !Objects.equals(toEdit.getSellerID(), sellerID)){
            result = "You do not own this item";
            logger.warn("Potential attack detected: Request to edit item user do not own");
        }
        return sessionKeyService.aesEncryptMessage(sessionID, result);
    }

    // Purchase item (note this only reduces the quantity of the item, it does not handle payment)
    // Also doubles as a check to ensure there is enough quantity of the item to purchase
    public boolean purchaseItem(Long itemID, Integer quantity) {
        boolean result = false;
        CatalogueItem existingItem = null;
        try{
            existingItem = catalogueItemRepository.getCatalogueItemByItemID(itemID);
            Integer remainingQuantity = existingItem.getItemQuantity() - quantity;

            // Check that the quantity is enough to cover transaction
            if(remainingQuantity == -1 || remainingQuantity >= 0){
                existingItem.setItemQuantity(remainingQuantity);
                catalogueItemRepository.save(existingItem);
                result = true;
            }
        }catch (Exception e){
            logger.error(e.getMessage());
        }
        return result;
    }

    // Allow user to disable their item listing
    public String disableItem(Long sellerID, Long itemID, Long sessionID) {
        String result = "";
        CatalogueItem toDisable = null;
        boolean continueProcess = false;

        // Get the item from DB
        try{
            toDisable = catalogueItemRepository.getCatalogueItemByItemID(itemID);
            continueProcess = true;
        }catch (Exception e){
            result = "Failed to disable item listing";
            logger.error(e.getMessage());
        }

        // Verify the request is made by the owner of the item
        if(continueProcess && Objects.equals(toDisable.getSellerID(), sellerID)){
            try{
                toDisable.setItemAvailable(Boolean.FALSE);
                catalogueItemRepository.save(toDisable);
                result = "Successfully disabled item listing";
            }catch (Exception e){
                result = "Failed to disable item listing";
                logger.error(e.getMessage());
            }
        }else if(continueProcess && !Objects.equals(toDisable.getSellerID(), sellerID)){
            result = "You do not own this item";
            logger.warn("Potential attack detected: Request to edit item user do not own");
        }
        return sessionKeyService.aesEncryptMessage(sessionID, result);
    }

}
