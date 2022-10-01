package rmit.sec.webstorepmicroservice.Catalogue.repository;

import org.springframework.stereotype.Repository;
import rmit.sec.webstorepmicroservice.Catalogue.model.CatalogueItem;
import rmit.sec.webstorepmicroservice.utils.ItemCatagory;

import java.util.List;

@Repository
public interface CatalogueItemRepository {

    CatalogueItem getCatalogueItemByItemID(Long itemID);

    // Return all items
    List<CatalogueItem> findAll();

    // Get array list of all items for a particular seller
    List<CatalogueItem> getCatalogueItemsBySellerID(Long sellerID);

    // Return arraylist of all items in the catalogue by matching category
    List<CatalogueItem> getCatalogueItemsByItemCategory(ItemCatagory itemCategory);

    // Return arraylist of all items in the catalogue availability
    List<CatalogueItem> getCatalogueItemsByItemAvailable(Boolean itemAvailable);

}
