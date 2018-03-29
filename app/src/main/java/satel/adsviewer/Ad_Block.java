package satel.adsviewer;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by satel on 3/25/2018.
 */

//TODO: add default values for all variables that might be null

@Entity(tableName = "adsDataBase")
public class Ad_Block implements Serializable {

    @Embedded
    @SerializedName("image") // Used to parse JSON
    private imageUrl imageUrl = new imageUrl();

    @Embedded
    private Price price = new Price();

    @ColumnInfo(name = "title")
    private String description = "No Description Available";


    private String location = "No Location Available";

    @PrimaryKey
    @NonNull
    @SerializedName("id") // Used to parse JSON
    private String imageID;

    private boolean isFavorited = false;

    public Ad_Block() {
    }

    @Ignore
    public Ad_Block(imageUrl imageUrl, Price price, String description,
                    String location, String imageID) {
        this.imageUrl = imageUrl;
        this.price = price;
        this.description = description;
        this.location = location;
        this.imageID = imageID;
    }

    @Ignore
    public Ad_Block(imageUrl imageUrl, Price price, String description,
                    String location, String title, boolean isFavorited,
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

    public imageUrl getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(imageUrl imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public int getPriceValue() {
        return price.getValue();
    }

    public void setPriceValue(int value) {
        this.price.setValue(value);
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

    public String getContent() {
        return price.getValue() + ",- in " + location;
    }

}