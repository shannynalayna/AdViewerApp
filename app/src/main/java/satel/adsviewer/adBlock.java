package satel.adsviewer;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by satel on 3/25/2018.
 * Class holds the information from the parsed JSON resource link:
 *
 */
public class adBlock implements Serializable {

    @SerializedName("image") // Used to parse JSON
    private imageUrl imageUrl = new imageUrl();

    private satel.adsviewer.price price = new price();

    private String description = "No Description Available";
    private String location = "No Location Available";

    @SerializedName("id") // Used to parse JSON
    private String imageID;

    private boolean isFavorited = false;

    public adBlock() {
    }

    public adBlock(imageUrl imageUrl, satel.adsviewer.price price, String description,
                   String location, String imageID) {
        this.imageUrl = imageUrl;
        this.price = price;
        this.description = description;
        this.location = location;
        this.imageID = imageID;
    }

    public adBlock(imageUrl imageUrl, satel.adsviewer.price price, String description,
                   String location, String title, boolean isFavorited,
                   String imageID) {
        this.imageUrl = imageUrl;
        this.price = price;
        this.description = description;
        this.location = location;

        this.isFavorited = isFavorited;
        this.imageID = imageID;

    }

    /* Getters and Setters for adBlock */
    public String getImageID() {
        return imageID;
    }

    public void setImageID(String imageID) {
        this.imageID = imageID;
    }

    public String getImageUrl() {
        return imageUrl.getImageUrl();
    }

    public void setImageUrl(imageUrl imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getPriceValue() {
        return price.getValue();
    }

    public void setPriceValue(satel.adsviewer.price priceValue) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public boolean getIsFavorited() {
        return isFavorited;
    }

    public void setIsFavorited(boolean isFavorited) {
        this.isFavorited = isFavorited;
    }

    /**
     * Intended for use when displaying ad information in card view
     * @return
     */
    public String getContent() {
        return price.getValue() + ",- in " + location;
    }

    public String toString() {
        return "[imageID=" + imageID + ", imageUrl=" + imageUrl + ", price=" + price +
                ", description=" + description + ", location=" + location +
                ", isFavorited" + isFavorited + "]";
    }
}