package satel.adsviewer;

import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
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

public class Ad_Activity extends AppCompatActivity {

    private static final String remoteJSON = "https://gist.githubusercontent.com/" +
            "3lvis/3799feea005ed49942dcb56386ecec2b/" +
            "raw/63249144485884d279d55f4f3907e37098f55c74/discover.json";

    private RequestQueue reqQueue;
    private Gson gson;


    private static List<Ad_Block> displayAds;
    private static List<Ad_Block> ads = new ArrayList<Ad_Block>();
    private static List<Ad_Block> favorites = new ArrayList<Ad_Block>();

    private boolean favoritesView = false;

    private ProgressBar adLoading;

    public Toast toast;
    public int duration = Toast.LENGTH_SHORT;


    private RecyclerView AdRecyclerView;
    private RecyclerView.Adapter AdAdapter;
    private RecyclerView.LayoutManager AdLayoutManager;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actionmenu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ad_activity_layout);
        Log.i("Init: ", "Here is that id: " + this.findViewById(R.id.recyclerViewList));


        reqQueue = Volley.newRequestQueue(this);

        adLoading = (ProgressBar) findViewById(R.id.adProgressBar);

        adLoading.setVisibility(View.VISIBLE);


        GsonBuilder gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();
        fetchAds();

        AdRecyclerView = (RecyclerView) findViewById(R.id.recyclerViewList);
        Log.i("Init Recycler view: ", AdRecyclerView.toString());
        // Using a linear Layout Manager

        AdLayoutManager = new LinearLayoutManager(this);
        Log.i("Init Recycler view: ", AdLayoutManager.toString());

        AdRecyclerView.setLayoutManager(AdLayoutManager);
    }

    private void fetchAds() {
        StringRequest req = new StringRequest(Request.Method.GET, remoteJSON,
                onAdsLoaded, onAdsError);
        reqQueue.add(req);
    }

    private final Response.Listener<String> onAdsLoaded = new Response.Listener<String>() {

        @Override
        public void onResponse(String resp) {
            try {
                JSONObject jsonObj = new JSONObject(resp);
                JSONArray items = jsonObj.getJSONArray("items");
                ads = Arrays.asList(gson.fromJson(String.valueOf(items), Ad_Block[].class));
                displayAds = ads;

                Log.i("Ad_Activity", "Ads loaded: " + ads.size());
                Log.i("Ad_Activity", "Ads Successfully Saved!");
                adLoading.setVisibility(View.GONE);


                AdAdapter = new AdBlockRecyclerViewAdapter(displayAds, getApplicationContext(), favoritesView);
                Log.i("Ad_Activity", "AdAdapter loaded");
                AdRecyclerView.setAdapter(AdAdapter);



            } catch (JSONException e) {
                Log.e("Ad_Activity", "Error saving ads");
                e.printStackTrace();
            }
        }
    };

    private final Response.ErrorListener onAdsError = new Response.ErrorListener() {

        @Override
        public void onErrorResponse(VolleyError err) {
            Log.e("ad_activity_layout", err.toString());
        }
    };

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

                            AdBlockRecyclerViewAdapter favoritesAdAdapter =
                                    new AdBlockRecyclerViewAdapter(favorites, getApplicationContext(),
                                            favoritesView);

                            adLoading.setVisibility(View.GONE);

                            AdRecyclerView.setAdapter(favoritesAdAdapter);


                            Log.i("Ad_Activity", "AdAdapter loaded");
                        }
                        else {
                            toast = Toast.makeText(getApplicationContext(), R.string.noFavorites, duration);
                            toast.show();



                            Log.i("Ad Activity", "No favorites to show");
                        }
                    }
                    else {

                        favoritesView = false;


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

    public static int favoriteCount(List<Ad_Block> ads) {
        int count = 0;
        for(Ad_Block ad : ads) {
            if(ad.getIsFavorited()) {
                count++;
            }
        }
        return count;
    }

    public static void updateFavorites() {
        for (Ad_Block ad : ads) {
            if (ad.getIsFavorited()) {
                favorites.add(ad);
            }
        }
    }

/*
    private static void updateMenu(Menu menu) {
        menu.findItem(R.id.action_favorite).setIconTintMode(PorterDuff.Mode.DARKEN);

    }
*/

}
