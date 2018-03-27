package satel.adsviewer;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by satel on 3/25/2018.
 */

//TODO: add default values for all variables that might be null
public class Ad_Block implements Serializable {

    @SerializedName("image") // Used to parse JSON
    private imageUrl imageUrl;


    private Price price;
    private String description;
    private String location;

    @SerializedName("id") // Used to parse JSON
    private String imageID;

    private int isFavorited = 0;

    public Ad_Block() {
    }

    public Ad_Block(imageUrl imageUrl, Price price, String description,
                    String location, String imageID) {
        this.imageUrl = imageUrl;
        this.price = price;
        this.description = description;
        this.location = location;
        this.imageID = imageID;
    }

    public Ad_Block(imageUrl imageUrl, Price price, String description,
                    String location, String title, int isFavorited,
                    String imageID) {
        this.imageUrl = imageUrl;
        this.price = price;
        this.description = description;
        this.location = location;

        this.isFavorited = isFavorited;
        this.imageID = imageID;

    }

    @Override
    public String toString() {
        // Need to rethink exactly what I might want to return here
        String ret;
        if( imageUrl != null && price != null && description != null) {
            ret = "Ad_Block{ " +
                    "imageID=" + imageID + ", " +
                    "priceValue=" + price.getValue() + ", " +
                    "description=" + description + "}";
        }
        else {
            ret = "Ad_Block{ " +
                    "imageID=" + imageID + "}";
        }
        return ret;

    }

    /* Getters and Setters for Ad_Block */
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

    public void setPriceValue(Price priceValue) {
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

    public int getIsFavorited() {
        return isFavorited;
    }

    public void setIsFavorited(int isFavorited) {
        this.isFavorited = isFavorited;
    }

    public String getContent() {
        return price.getValue() + ",- in " + location;
    }

}