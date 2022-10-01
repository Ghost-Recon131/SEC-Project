package rmit.sec.webstorepmicroservice.Catalogue.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;
import rmit.sec.webstorepmicroservice.utils.ItemCatagory;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "catalogueItem")
public class CatalogueItem {

    // SQL primary key
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long itemID;

    // Will link to the account ID of whoever listed the item
    @Column(name = "seller_id")
    private Long sellerID;

    // Length limit default of 255
    @Column(name = "item_name")
    private String itemName;

    // Length limit default of 255
    @Column(name = "item_description", length = 10000)
    private String itemDescription;

    @Column(name = "item_available")
    private Boolean itemAvailable;

    @Column(name = "item_price")
    private Double itemPrice;

    @Column(name = "item_quantity")
    private Integer itemQuantity;

    @Column(name = "item_image")
    private String itemImage;

    @Enumerated(EnumType.STRING)
    @Column(name = "item_category")
    private ItemCatagory itemCategory;

    // Constructor
    public CatalogueItem(Long sellerID, String itemName, String itemDescription, Boolean itemAvailable, Double itemPrice,
                         Integer itemQuantity, String itemImage, ItemCatagory itemCategory) {
        this.sellerID = sellerID;
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.itemAvailable = itemAvailable;
        this.itemPrice = itemPrice;
        this.itemQuantity = itemQuantity;
        this.itemImage = itemImage;
        this.itemCategory = itemCategory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        CatalogueItem catalogueItem = (CatalogueItem) o;
        return itemID != null && Objects.equals(itemID, catalogueItem.itemID);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
