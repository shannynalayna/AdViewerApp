package satel.adsviewer;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

/**
 * Created by satel on 3/25/2018.
 */

@Entity(foreignKeys = @ForeignKey(entity = Ad_Block.class,
                                    parentColumns = "price_value",
                                    childColumns = "value"))
public class Price implements Serializable {

    @PrimaryKey
    private int value = 0;

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
