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
public interface AdDao {

    @Insert
    public void insertAd(Ad_Block ad);

    @Insert
    public void insertAds(List<Ad_Block> ads);

    @Query("SELECT COUNT(*) FROM adsDataBase WHERE imageID != Null")
    public int adCount();


    @Delete
    public void deleteAds(Ad_Block... ads);

    @Query("SELECT * FROM adsDataBase")
    public List<Ad_Block> loadAllAds();

    @Query("SELECT * FROM adsDataBase WHERE isFavorited = :favorited")
    public List<Ad_Block> loadFavoriteAds(boolean favorited);


}
