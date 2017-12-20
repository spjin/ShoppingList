package hu.ait.android.shoppinglist.data;

import hu.ait.android.shoppinglist.R;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by sarahjin on 11/8/17.
 */

public class Item extends RealmObject {

    @PrimaryKey
    private String itemID;

    private String itemName;
    private String itemDescription;
    private String category;
    private String createDate;
    private String price;
    private boolean purchased;

    public Item() {
    }

    public Item(String itemName, String itemDescription, String category, String createDate, String price, boolean purchased) {
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.category = category;
        this.createDate = createDate;
        this.price = price;
        this.purchased = purchased;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public boolean isPurchased() {
        return purchased;
    }

    public void setPurchased(boolean purchased) {
        this.purchased = purchased;
    }

    public String getItemID(){
        return itemID;
    }
}
