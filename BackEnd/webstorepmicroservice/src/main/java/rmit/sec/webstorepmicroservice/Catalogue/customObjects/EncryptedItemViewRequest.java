package rmit.sec.webstorepmicroservice.Catalogue.customObjects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EncryptedItemViewRequest {
    private String itemID;
    private String sellerID;
    private String category;
}
