package satel.adsviewer;

import java.io.Serializable;

/**
 * Created by satel on 3/25/2018.
 */

public class Price implements Serializable {

    private int value = 0;

    public Price() {    }

    public Price (int value) {
        this.value = value;
    }

    // Getters & Setters for Price Class
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
