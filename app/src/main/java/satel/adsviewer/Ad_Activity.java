package satel.adsviewer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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
    private List<Ad_Block> ads;

    private RecyclerView AdRecyclerView;
    private RecyclerView.Adapter AdAdapter;
    private RecyclerView.LayoutManager AdLayoutManager;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad_);

        reqQueue = Volley.newRequestQueue(this);


        GsonBuilder gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();
        fetchAds();

        AdRecyclerView = (RecyclerView) findViewById(R.id.recyclerViewList);

        // Using a linear Layout Manager
        AdLayoutManager = new LinearLayoutManager(this);
        AdRecyclerView.setLayoutManager(AdLayoutManager);

        // Specifying an adapter, need a listener
        //TODO: Figure out this whole listener business
        AdAdapter = new AdBlockRecyclerViewAdapter(ads, Listener);
        AdRecyclerView.setAdapter(AdAdapter);


        //TODO: Figure out how to display the images from the urls


        TextView textView = findViewById(R.id.textView01);
        textView.setText("We've fetched the ads");
    }

    private void fetchAds() {
        StringRequest req = new StringRequest(Request.Method.GET, remoteJSON,
                onAdsLoaded, onAdsError);
        reqQueue.add(req);
    }

    private final Response.Listener<String> onAdsLoaded = new Response.Listener<String>() {

        @Override
        public void onResponse(String resp) {
            //
            try {
                JSONObject jsonObj = new JSONObject(resp);
                JSONArray items = jsonObj.getJSONArray("items");
                ads = Arrays.asList(gson.fromJson(String.valueOf(items), Ad_Block[].class));
                Log.i("Ad_Activity", "Ads loaded: " + ads.size());
                Log.i("Ad_Activity", "Ads Successfully Saved!");

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
            Log.e("activity_ad_", err.toString());
        }
    };

    public void onClick(View v) {
        startActivity(new Intent(this, Ad_Activity.class));
        finish();
    }




}
