package satel.adsviewer;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class AdsViewerLogic {

    public Toast toast;
    public int duration = Toast.LENGTH_SHORT;


    public boolean switchToFavoriteView(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_favorite:
                try {
                    if(!favoritesView) {
                        int favoriteCount = favoriteCount(ads);
                        if(favoriteCount != 0) {
                            favoritesView = true;
                            adLoading.setVisibility(View.VISIBLE);
                            updateFavorites();

                            setTitle(R.string.FavoritesDisplay);

                            AdBlockRecyclerViewAdapter favoritesAdAdapter =
                                    new AdBlockRecyclerViewAdapter(favorites, getApplicationContext(),
                                            favoritesView);

                            adLoading.setVisibility(View.GONE);

                            AdRecyclerView.setAdapter(favoritesAdAdapter);
                        }
                        else {
                            toast = Toast.makeText(getApplicationContext(), R.string.noFavorites, duration);
                            toast.show();
                        }
                    }
                    else {
                        favoritesView = false;
                        setTitle(R.string.app_name);
                        AdRecyclerView.setAdapter(adAdapter);
                    }
                } catch (Exception e) {
                    Log.e("onOptionsItemSelected", "Error Loading Favorite Ads");
                    e.printStackTrace();
                }

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    /**
     * Updating favorites in the case that the user has selected / deselected certain ads
     * independent of which view is being displayed
     */
    public static void updateFavorites() {
        favorites = new ArrayList<Ad_Block>();
        for (Ad_Block ad : ads) {
            if (ad.getIsFavorited()) {
                favorites.add(ad);
            }
        }
    }

}
