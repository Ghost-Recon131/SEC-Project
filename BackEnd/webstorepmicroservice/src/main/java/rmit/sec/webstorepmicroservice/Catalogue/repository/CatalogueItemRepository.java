package rmit.sec.webstorepmicroservice.Catalogue.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rmit.sec.webstorepmicroservice.Catalogue.model.CatalogueItem;
import rmit.sec.webstorepmicroservice.utils.ItemCatagory;

import java.util.List;

@Repository
public interface CatalogueItemRepository extends JpaRepository<CatalogueItem, Long> {

    CatalogueItem getCatalogueItemByItemID(Long itemID);

    // Return all items
    List<CatalogueItem> findAll();

    // Get array list of all items for a particular seller
    List<CatalogueItem> findAllBySellerID(Long sellerID);

    // Return arraylist of all items in the catalogue by matching category
    List<CatalogueItem> findAllByItemCategory(ItemCatagory itemCategory);

    // Return arraylist of all items in the catalogue availability
    List<CatalogueItem> findAllByItemAvailable(Boolean itemAvailable);

}
