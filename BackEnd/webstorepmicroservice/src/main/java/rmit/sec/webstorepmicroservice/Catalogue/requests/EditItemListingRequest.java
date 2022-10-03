package rmit.sec.webstorepmicroservice.Catalogue.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
public class EditItemListingRequest {
    private Long itemID;
    private String itemName;
    private String itemDescription;
    private Boolean itemAvailable;
    private Double itemPrice;
    private Integer itemQuantity;
    private String itemImage;
    private String itemCategory;
}
