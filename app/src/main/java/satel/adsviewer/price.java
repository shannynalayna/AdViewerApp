package satel.adsviewer;

import java.io.Serializable;

/**
 * Created by satel on 3/25/2018.
 */

@SuppressWarnings("unused")
public class price implements Serializable {

    private int value = 0;

    price() {}

    // Getters & Setters for price Class
    public int getValue() {
        return value;
    }

    /**
     * @param value value of price, nested inside JSON resource
     */
    void setValue(int value) {
        this.value = value;
    }
}
