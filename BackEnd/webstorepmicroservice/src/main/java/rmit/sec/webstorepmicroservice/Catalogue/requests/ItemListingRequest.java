package rmit.sec.webstorepmicroservice.Catalogue.requests;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class ItemListingRequest {
    private String itemName;
    private String itemDescription;
    private Boolean itemAvailable;
    private Double itemPrice;
    private Integer itemQuantity;
    private String itemImage;
    private String itemCategory;
}
