package satel.adsviewer;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Created by satel on 3/28/2018.
 */

@Dao
public interface adDao {

    @Insert
    public void insertAd(Ad_Block ad);

    @Insert
    public void insertAds(List<Ad_Block> ads);

    @Delete
    public void deleteAds(Ad_Block... ads);

    @Query("SELECT * FROM adsDataBase")
    public Ad_Block[] loadAllAds();

    @Query("SELECT * FROM adsDataBase WHERE isFavorited = 1")
    public Ad_Block[] loadFavoriteAds();


}
