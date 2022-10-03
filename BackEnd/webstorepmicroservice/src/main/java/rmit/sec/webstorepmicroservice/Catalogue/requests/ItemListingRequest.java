package rmit.sec.webstorepmicroservice.Catalogue.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
public class ItemListingRequest {
    private String itemName;
    private String itemDescription;
    private Double itemPrice;
    private Integer itemQuantity;
    private String itemImage;
    private String itemCategory;
}
