package rmit.sec.webstorepmicroservice.Catalogue.customObjects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class EncryptedCatalogueItem {
    private String itemID;
    private String sellerID;
    private String itemName;
    private String itemDescription;
    private String itemAvailable;
    private String itemPrice;
    private String itemQuantity;
    private String itemImage;
    private String itemCategory;
}
