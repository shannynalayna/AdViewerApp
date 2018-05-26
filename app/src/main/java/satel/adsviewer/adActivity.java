package satel.adsviewer;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
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
 * Main Activity for application:8
 *
 *  - Fetches ads from the remote JSON resource
 *  - Maintains overall list of ads as well as ads marked 'favorite"
 *  - Creates / inflates the view for the application activity
 *      - Recycler view utilized to make scrolling through ads lists easier
 *  - Creates the adapter for the recycler view used for this application
 */
public class adActivity extends AppCompatActivity {

    private String remoteJSON;
    private RequestQueue reqQueue;
    private Gson gson;
    private static List<adBlock> ads = new ArrayList<>();
    private ProgressBar adLoading;
    private Toast toast;
    private final int duration = Toast.LENGTH_SHORT;
    private RecyclerView adRecyclerView;
    private RecyclerView.Adapter adAdapter;


    /**
     * @param menu Option Menu
     * @return super
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /* Inflating the menu to enable the view switch button
          between which list of ads to view
         */
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actionmenu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity);

        adLogic.setContext(this);

        reqQueue = Volley.newRequestQueue(this);
        adLoading = findViewById(R.id.adProgressBar);
        adLoading.setVisibility(View.VISIBLE);

        remoteJSON = getString(R.string.remoteJSONresource);
        GsonBuilder gsonBuilder = new GsonBuilder();
        gson = gsonBuilder.create();

        adRecyclerView = findViewById(R.id.recyclerViewList);

        RecyclerView.LayoutManager adLayoutManager = new GridLayoutManager(this, 2);

        adRecyclerView.setLayoutManager(adLayoutManager);


        // Check here if shared preferences is empty ?
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String jsonAdsString = preferences.getString("persistAds", null);
        if(jsonAdsString != null && !jsonAdsString.isEmpty()) {

            ads = Arrays.asList(gson.fromJson(jsonAdsString, adBlock[].class));
            toast = Toast.makeText(getApplicationContext(), R.string.ads_loaded, duration);
            toast.show();
            setView();
        }
        else {
            fetchAds();
        }


    }

    /**
     * Making request separate from main thread
     */
    private void fetchAds() {
        StringRequest req = new StringRequest(Request.Method.GET, remoteJSON,
                onAdsLoaded, onAdsError);
        reqQueue.add(req);
    }

    private void setView() {
        adLoading.setVisibility(View.GONE);
        adAdapter = new adBlockRecyclerViewAdapter(ads, getApplicationContext());
        adRecyclerView.setAdapter(adAdapter);
    }

    /**
     * Listening for a response from the JSON resource, meant to catch any possible errors
     * when making this connection
     */
    private final Response.Listener<String> onAdsLoaded = new Response.Listener<String>() {

        /**
         * @param resp Response from JSON req
         */
        @Override
        public void onResponse(String resp) {
            try {
                //This is where we are making the request to the JSON Object to get the ads
                JSONObject jsonObj = new JSONObject(resp);
                JSONArray items = jsonObj.getJSONArray("items");
                ads = Arrays.asList(gson.fromJson(String.valueOf(items), adBlock[].class));

                Log.i("adActivity", "Ads Successfully Saved!");

                toast = Toast.makeText(getApplicationContext(), R.string.ads_loaded, duration);
                toast.show();
                setView();
            } catch (JSONException e) {
                toast = Toast.makeText(getApplicationContext(), R.string.fail_ads_loaded, duration);
                toast.show();
                Log.e("adActivity", "Error saving ads");
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
            toast = Toast.makeText(getApplicationContext(), R.string.fail_ads_loaded, duration);
            toast.show();
            Log.e("adActivityLayout", err.toString());
        }
    };


    @Override
    protected void onPause() {
        super.onPause();
        adLogic.saveState(ads, gson, this);
    }

    /**
     * @param item Item selected from options menu
     * @return success / failure bool from menu item selected and task performed
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /*
        * Handle presses on the action bar items,
        * switch statement enables further modification, more action bar items
        */

        switch (item.getItemId()) {
            case R.id.action_favorite:
                try {
                    adRecyclerView.setAdapter(adLogic.getAdAdapter(ads,
                            getApplicationContext(), adLoading));
                } catch (Exception e) {
                    Log.e("Menu Item Selected", "Error Displaying Favorite Ads");
                    e.printStackTrace();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
