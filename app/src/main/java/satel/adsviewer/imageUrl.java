package satel.adsviewer;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

/**
 * Created by satel on 3/25/2018.
 */

public class imageUrl implements Serializable {


    @ColumnInfo(name = "ImageUrl")
    private String url;

    public imageUrl() {

    }

    @Ignore
    public imageUrl(String url) {
        this.url = url;
    }

    // Getters & Setters for imageUrl Class
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
