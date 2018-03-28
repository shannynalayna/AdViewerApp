package satel.adsviewer;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

/**
 * Created by satel on 3/25/2018.
 */

@Entity(foreignKeys = @ForeignKey(entity = Ad_Block.class,
                                    parentColumns = "imageUrl",
                                    childColumns = "url"))
public class imageUrl implements Serializable {

    @PrimaryKey
    private String url;
    private String appendUrl = "https://images.finncdn.no/dynamic/480x360c/";

    public imageUrl() {

    }

    public imageUrl(String url) {
        this.url = url;
    }

    // Getters & Setters for imageUrl Class
    public String getImageUrl() {
        return appendUrl + url;
    }

    public void setImageUrl(String url) {
        this.url = url;
    }
}
