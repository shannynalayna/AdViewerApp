package satel.adsviewer;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
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

    private static boolean favoritesView = false;
    private static Toast toast;
    private static final int duration = Toast.LENGTH_SHORT;

    private static String remoteJSON = "https://gist.githubusercontent.com/3lvis/3799feea005ed49942dcb56386ecec2b/raw/63249144485884d279d55f4f3907e37098f55c74/discover.json";
    private static RequestQueue reqQueue;
    private static GsonBuilder gsonBuilder = new GsonBuilder();
    private static Gson gson = gsonBuilder.create();


    private static List<adBlock> ads;

    private static Context context;



    /* TODO: Fill in the logic for the rest of the application, serves as a middleman
 * Previous attempt at separating the concerns had moved the following methods here:
 *  - switchToFavoriteView
 *  - updateFavorites
 */

    /*
     * The following methods replaced those originally used in adActivity
     */
    static List<adBlock> updateFavorites(List<adBlock> ads) {
        List<adBlock> favorites = new ArrayList<>();
        for (adBlock ad : ads) {
            if (ad.getIsFavorited()) {
                favorites.add(ad);
            }
        }
        return favorites;
    }


    static int favoriteCount(List<adBlock> ads) {
        int count = 0;
        for (adBlock ad : ads) {
            if(ad.getIsFavorited()) {
                count++;
            }
        }
        return count;
    }

    static boolean getFavoritesView() {
        return favoritesView;
    }

    static void setFavoritesView(boolean choice) {
        favoritesView = choice;
    }

    static adBlockRecyclerViewAdapter getAdAdapter(List<adBlock> ads,
                                                                      Context context,
                                                                      ProgressBar adLoading) {
        adBlockRecyclerViewAdapter adAdapter = new adBlockRecyclerViewAdapter(ads, context);
        if(!favoritesView) {
            if(favoriteCount(ads) != 0) {
                // Display the favorited ads
                adLoading.setVisibility(View.VISIBLE);
                setFavoritesView(true);
                adAdapter = new adBlockRecyclerViewAdapter(updateFavorites(ads),
                        context);
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

    static void saveState(List<adBlock> ads, Gson gson, Context context) {
        String adsStringList = gson.toJson(ads);
        SharedPreferences.Editor editor = PreferenceManager
                .getDefaultSharedPreferences(context).edit();
        editor.putString("persistAds", adsStringList);
        editor.apply();
        editor.commit();
    }

    static void populateAds(Gson gson){
        List<adBlock> ads;
        // First need to check if the ads are stored in local preferences
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String jsonAdsString = preferences.getString("persistAds", null);
        if(jsonAdsString != null && !jsonAdsString.isEmpty()) {
            // Then we can load the ads list from our shared preferences
            ads = Arrays.asList(gson.fromJson(jsonAdsString, adBlock[].class));
            toast = Toast.makeText(context, "Ads successfully loaded!", duration);
            toast.show();

            // TODO: UPDATE THE adAdapter
        }
        else {
            reqQueue = Volley.newRequestQueue(context);
            fetchAds();
        }
    }

    private static void fetchAds(){
        StringRequest req = new StringRequest(Request.Method.GET, remoteJSON,
                onAdsLoaded, onAdsError);
        reqQueue.add(req);
    }

    private static final Response.Listener<String> onAdsLoaded = new Response.Listener<String>() {

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
                //TODO: Set view
            } catch (JSONException e) {
                toast = Toast.makeText(context, "Ads cannot be loaded", duration);
                toast.show();
                e.printStackTrace();
            }
        }
    };

    private static final Response.ErrorListener onAdsError = new Response.ErrorListener() {

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





    /*
     * The following methods replaced those originally used in adBlockRecyclerViewAdapter
     */
    static int maintainFavoriteIndication(adBlock ad) {
        int returnResID;
        if(ad.getIsFavorited()) {
            returnResID = R.drawable.favorited;
        }
        else returnResID = R.drawable.favorite_button;

        return returnResID;
    }

    static int adInteraction(adBlock ad) {
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





    static List<adBlock> getAds(){
        return ads;
    }

    static void setContext(Context activityContext) {
        context = activityContext;
    }











}
