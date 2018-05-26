package satel.adsviewer;


import java.io.Serializable;

/**
 * Created by satel on 3/25/2018.
 */

@SuppressWarnings("unused")
public class imageUrl implements Serializable {

    private String url;

    imageUrl() {    }

    // Getters & Setters for imageUrl Class
    String getImageUrl() {
        return url;
    }

    void setImageUrl(String url) {
        this.url = url;
    }
}
