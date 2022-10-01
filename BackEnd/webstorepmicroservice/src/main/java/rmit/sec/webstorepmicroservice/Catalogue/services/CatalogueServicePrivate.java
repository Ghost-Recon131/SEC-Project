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
import rmit.sec.webstorepmicroservice.utils.ItemCatagory;


@Service
@AllArgsConstructor
public class CatalogueServicePrivate {
    @Autowired
    private CatalogueItemRepository catalogueItemRepository;

    private final Logger logger = LogManager.getLogger(this.getClass());

    // List new item
    public String listItem(ItemListingRequest request) {
        String result = "";
        CatalogueItem newItem = null;
        try{
            newItem = new CatalogueItem(
                    request.getSellerID(),
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
        return result;
    }

    // TODO: Edit item
    public String editItem(EditItemListingRequest request) {
        String result = "";
        CatalogueItem toEdit = null;
        try{
            toEdit = catalogueItemRepository.getCatalogueItemByItemID(request.getItemID());
        }catch (Exception e){
            result = "Failed to edit item listing";
            logger.error(e.getMessage());
        }

        return result;
    }

    // TODO: Purchase item

}
