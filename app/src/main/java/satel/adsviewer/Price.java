package satel.adsviewer;

import android.arch.persistence.room.Entity;

import java.io.Serializable;

/**
 * Created by satel on 3/25/2018.
 */

@Entity
public class Price implements Serializable {

    private int value;

    public Price() {

    }

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
