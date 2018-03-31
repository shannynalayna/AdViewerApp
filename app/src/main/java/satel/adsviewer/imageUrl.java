package satel.adsviewer;


import java.io.Serializable;

/**
 * Created by satel on 3/25/2018.
 */

public class imageUrl implements Serializable {

    private String url;

    public imageUrl() {    }

    public imageUrl(String url) {
        this.url = url;
    }

    // Getters & Setters for imageUrl Class
    public String getImageUrl() {
        return url;
    }

    public void setImageUrl(String url) {
        this.url = url;
    }
}
