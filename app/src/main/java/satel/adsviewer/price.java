package satel.adsviewer;

import java.io.Serializable;

/**
 * Created by satel on 3/25/2018.
 */

public class price implements Serializable {

    private int value = 0;

    public price() {    }

    public price(int value) {
        this.value = value;
    }

    // Getters & Setters for price Class
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
