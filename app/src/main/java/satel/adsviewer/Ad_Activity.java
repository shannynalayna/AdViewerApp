package satel.adsviewer;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

/**
 * Main Activity for application:
 *
 *  - Fetches ads from the remote JSON resource
 *  - Maintains overall list of ads as well as ads marked 'favorite"
 *  - Creates / inflates the view for the application activity
 *      - Recycler view utilized to make scrolling through ads lists easier
 *  - Creates the adapter for the recycler view used for this application
 */
public class Ad_Activity extends AppCompatActivity {

    private String remoteJSON;
    private RequestQueue reqQueue;
    private Gson gson;
    private static List<Ad_Block> ads = new ArrayList<Ad_Block>();
    private static List<Ad_Block> favorites = new ArrayList<Ad_Block>();
    private boolean favoritesView = false;
    private ProgressBar adLoading;
    public Toast toast;
    public int duration = Toast.LENGTH_SHORT;
    private RecyclerView AdRecyclerView;
    private RecyclerView.Adapter AdAdapter;


    /**
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /** Inflating the menu to enable the view switch button
         * between which list of ads to view
         */
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actionmenu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ad_activity_layout);

        reqQueue = Volley.newRequestQueue(this);
        adLoading = (ProgressBar) findViewById(R.id.adProgressBar);
        adLoading.setVisibility(View.VISIBLE);

        remoteJSON = getString(R.string.remoteJSONresource);
        GsonBuilder gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();
        fetchAds();

        AdRecyclerView = (RecyclerView) findViewById(R.id.recyclerViewList);

        RecyclerView.LayoutManager adLayoutManager = new LinearLayoutManager(this);

        AdRecyclerView.setLayoutManager(adLayoutManager);
    }

    /**
     * Making request separate from main thread
     */
    private void fetchAds() {
        StringRequest req = new StringRequest(Request.Method.GET, remoteJSON,
                onAdsLoaded, onAdsError);
        reqQueue.add(req);
    }

    /**
     * Listening for a response from the JSON resource, meant to catch any possible errors
     * when making this connection
     */
    private final Response.Listener<String> onAdsLoaded = new Response.Listener<String>() {

        /**
         * @param resp
         */
        @Override
        public void onResponse(String resp) {
            try {
                JSONObject jsonObj = new JSONObject(resp);
                JSONArray items = jsonObj.getJSONArray("items");
                ads = Arrays.asList(gson.fromJson(String.valueOf(items), Ad_Block[].class));

                Log.i("Ad_Activity", "Ads Successfully Saved!");
                toast = Toast.makeText(getApplicationContext(), R.string.ads_loaded, duration);
                toast.show();
                adLoading.setVisibility(View.GONE);
                AdAdapter = new AdBlockRecyclerViewAdapter(ads, getApplicationContext(),
                                                            favoritesView);
                AdRecyclerView.setAdapter(AdAdapter);
            } catch (JSONException e) {
                toast = Toast.makeText(getApplicationContext(), R.string.fail_ads_loaded, duration);
                toast.show();
                Log.e("Ad_Activity", "Error saving ads");
                e.printStackTrace();
            }
        }
    };
    private final Response.ErrorListener onAdsError = new Response.ErrorListener() {

        /**
         * @param err
         */
        @Override
        public void onErrorResponse(VolleyError err) {
            toast = Toast.makeText(getApplicationContext(), R.string.fail_ads_loaded, duration);
            toast.show();
            Log.e("ad_activity_layout", err.toString());
        }
    };

    /**
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items

        //switch statement enables further modification, more action bar items
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
                        AdRecyclerView.setAdapter(AdAdapter);
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
     * @param ads
     * @return
     */
    public static int favoriteCount(List<Ad_Block> ads) {
        int count = 0;
        for(Ad_Block ad : ads) {
            if(ad.getIsFavorited()) {
                count++;
            }
        }
        return count;
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
