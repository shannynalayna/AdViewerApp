package satel.adsviewer;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

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

import java.util.Arrays;
import java.util.List;

public class Ad_Activity extends AppCompatActivity {

    private static final String remoteJSON = "https://gist.githubusercontent.com/" +
            "3lvis/3799feea005ed49942dcb56386ecec2b/" +
            "raw/63249144485884d279d55f4f3907e37098f55c74/discover.json";

    private RequestQueue reqQueue;
    private Gson gson;

    private static List<Ad_Block> ads;
    private static List<Ad_Block> favorites;

    private ProgressBar adLoading;

    private RecyclerView AdRecyclerView;
    private RecyclerView.Adapter AdAdapter;
    private RecyclerView.LayoutManager AdLayoutManager;



    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ad_activity_layout);
        Log.i("Init: " , "Here is that id: " + this.findViewById(R.id.recyclerViewList));


        reqQueue = Volley.newRequestQueue(this);

        adLoading = (ProgressBar)findViewById(R.id.adProgressBar);

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

                for (int i = 0; i < ads.size(); i++ ) {
                    Ad_Block ad = ads.get(i);
                    if(ad.getDescription() == null || ad.getLocation() == null ||
                            ad.getImageUrl() == null) {
                        ads.remove(i);
                    }
                }
                Log.i("Ad_Activity", "Ads loaded: " + ads.size());
                Log.i("Ad_Activity", "Ads Successfully Saved!");
                adLoading.setVisibility(View.GONE);


                AdAdapter = new AdBlockRecyclerViewAdapter(ads, getApplicationContext());
                Log.i("Ad_Activity", "AdAdapter loaded");
                AdRecyclerView.setAdapter(AdAdapter);




                // for (Ad_Block ad : ads) {
                // }
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




}
