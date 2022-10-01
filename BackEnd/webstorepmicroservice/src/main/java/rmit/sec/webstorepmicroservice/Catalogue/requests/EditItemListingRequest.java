package rmit.sec.webstorepmicroservice.Catalogue.requests;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class EditItemListingRequest {
    private Long itemID;
    private Long sellerID;
    private String itemName;
    private String itemDescription;
    private Boolean itemAvailable;
    private Double itemPrice;
    private Integer itemQuantity;
    private String itemImage;
    private String itemCategory;
}
