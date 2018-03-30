package satel.adsviewer;

import android.app.Fragment;
import android.os.Bundle;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Ad_Block_DataFragment extends Fragment {

    private List<Ad_Block> ads;
    private List<Ad_Block> favorites;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public void setData(List<Ad_Block> ads, List<Ad_Block> favorites) {
        this.ads = ads;
        this.favorites = favorites;
    }

    public void setAds(List<Ad_Block> ads) {
        this.ads = ads;
    }

    public List<Ad_Block> getAds() {
        return ads;
    }

    public void setFavorites(List<Ad_Block> favorites) {
        this.favorites = favorites;
    }

    public List<Ad_Block> getFavorites() {
        return favorites;
    }


}
