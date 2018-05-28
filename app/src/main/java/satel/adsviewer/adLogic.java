package satel.adsviewer;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class adLogic {

    private boolean favoritesView = false;
    private Toast toast;
    private final int duration = Toast.LENGTH_SHORT;

    private RequestQueue reqQueue;
    private final GsonBuilder gsonBuilder = new GsonBuilder();
    private final Gson gson = gsonBuilder.create();

    private RecyclerView.Adapter adAdapter;

    private List<adBlock> ads;

    private final Context context;

    adLogic(adActivity adActivity) {
        this.context = adActivity;
    }


/* Previous attempt at separating the concerns had moved the following methods here:
 *  - switchToFavoriteView
 *  - updateFavorites

     * The following methods replaced those originally used in adActivity
     */
    private List<adBlock> updateFavorites(List<adBlock> ads) {
        List<adBlock> favorites = new ArrayList<>();
        for (adBlock ad : ads) {
            if (ad.getIsFavorited()) {
                favorites.add(ad);
            }
        }
        return favorites;
    }


    private int favoriteCount(List<adBlock> ads) {
        int count = 0;
        for (adBlock ad : ads) {
            if(ad.getIsFavorited()) {
                count++;
            }
        }
        return count;
    }

    private void setFavoritesView(boolean choice) {
        favoritesView = choice;
    }

    adBlockRecyclerViewAdapter getAdAdapter(ProgressBar adLoading) {
        adBlockRecyclerViewAdapter adAdapter = new adBlockRecyclerViewAdapter(ads, context, this);
        if(!favoritesView) {
            if(favoriteCount(ads) != 0) {
                // Display the favorited ads
                adLoading.setVisibility(View.VISIBLE);
                setFavoritesView(true);
                adAdapter = new adBlockRecyclerViewAdapter(updateFavorites(ads),
                        context, this);
                adLoading.setVisibility(View.GONE);
                toast = Toast.makeText(context, "Displaying Favorites!", duration);
                toast.show();
            }
            else {
                toast = Toast.makeText(context, "No Favorites to show!", duration);
                toast.show();
            }
        }
        else {
            setFavoritesView(false);
        }
        return adAdapter;
    }

    void saveState() {
        String adsStringList = gson.toJson(ads);
        SharedPreferences.Editor editor = PreferenceManager
                .getDefaultSharedPreferences(context).edit();
        editor.putString("persistAds", adsStringList);
        editor.apply();
        editor.commit();
    }

    void populateAds(){
        // First need to check if the ads are stored in local preferences
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String jsonAdsString = preferences.getString("persistAds", null);
        if(jsonAdsString != null && !jsonAdsString.isEmpty()) {
            // Then we can load the ads list from our shared preferences
            ads = Arrays.asList(gson.fromJson(jsonAdsString, adBlock[].class));
            toast = Toast.makeText(context, "Ads successfully loaded!", duration);
            toast.show();
            updateAdapter();
        }
        else {
            reqQueue = Volley.newRequestQueue(context);
            fetchAds();
        }
    }

    private void fetchAds(){
        String remoteJSON = "https://gist.githubusercontent.com/3lvis/3799feea005ed49942dcb56386ecec2b/raw/63249144485884d279d55f4f3907e37098f55c74/discover.json";
        StringRequest req = new StringRequest(Request.Method.GET, remoteJSON,
                onAdsLoaded, onAdsError);
        reqQueue.add(req);
    }

    private final Response.Listener<String> onAdsLoaded = new Response.Listener<String>() {

        /**
         * @param resp Response from JSON req
         */
        @Override
        public void onResponse(String resp) {
            try {
                JSONObject jsonObj = new JSONObject(resp);
                JSONArray items = jsonObj.getJSONArray("items");
                ads = Arrays.asList(gson.fromJson(String.valueOf(items), adBlock[].class));
                toast = Toast.makeText(context, R.string.ads_loaded, duration);
                toast.show();
                updateAdapter();
            } catch (JSONException e) {
                toast = Toast.makeText(context, "Ads cannot be loaded", duration);
                toast.show();
                e.printStackTrace();
            }
        }
    };

    private final Response.ErrorListener onAdsError = new Response.ErrorListener() {

        /**
         * @param err Error getting response
         */
        @Override
        public void onErrorResponse(VolleyError err) {
            toast = Toast.makeText(context, "There was an error loading ads", duration);
            toast.show();
            Log.e("Response Error", err.toString());
        }
    };

    void setViewAdapter(RecyclerView v) {
        v.setAdapter(adAdapter);
    }



    /*
     * The following methods replaced those originally used in adBlockRecyclerViewAdapter
     */
    int maintainFavoriteIndication(adBlock ad) {
        int returnResID;
        if(ad.getIsFavorited()) {
            returnResID = R.drawable.favorited;
        }
        else returnResID = R.drawable.favorite_button;

        return returnResID;
    }

    int adInteraction(adBlock ad) {
        int returnResID;
        boolean choice = !ad.getIsFavorited();
        if(!ad.getIsFavorited()) {
            ad.setIsFavorited(choice);
            returnResID = R.drawable.favorited;
        }
        else {
            ad.setIsFavorited(choice);
            returnResID = R.drawable.favorite_button;
        }
        return returnResID;
    }


    private void updateAdapter() {
        adAdapter = new adBlockRecyclerViewAdapter(ads, context, this);
    }










}
