package satel.adsviewer;

import android.annotation.SuppressLint;
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

/*
 * Main Activity for application:8
 *  - Fetches ads from the remote JSON resource
 *  - Maintains overall list of ads as well as ads marked 'favorite"
 *  - Creates / inflates the view for the application activity
 *      - Recycler view utilized to make scrolling through ads lists easier
 *  - Creates the adapter for the recycler view used for this application
 */
public class adActivity extends AppCompatActivity {

    private ProgressBar adLoading;
    private RecyclerView adRecyclerView;
    private adLogic controller;

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
        adLoading = findViewById(R.id.adProgressBar);
        adLoading.setVisibility(View.VISIBLE);
        adRecyclerView = findViewById(R.id.recyclerViewList);
        RecyclerView.LayoutManager adLayoutManager = new GridLayoutManager(this, 2);
        adRecyclerView.setLayoutManager(adLayoutManager);
        controller = new adLogic(this);
        controller.populateAds();
        adLoading.setVisibility(View.GONE);
        controller.setViewAdapter(adRecyclerView);
    }

    @Override
    protected void onPause() {
        super.onPause();
        controller.saveState();
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
                    adRecyclerView.setAdapter(controller.getAdAdapter(adLoading));
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