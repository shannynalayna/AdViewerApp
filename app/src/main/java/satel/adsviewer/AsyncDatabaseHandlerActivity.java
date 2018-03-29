package satel.adsviewer;

import android.app.Activity;
import android.os.Bundle;

public class AsyncDatabaseHandlerActivity extends Activity {

    private adDatabase adsDatabase;
    private AdDao adDao;
    private int adCount;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ad_activity_layout);

    }

    @Override
    protected void doInBackground(Void... params) {

    }


    /**
     *         AgentDao agentDao = MyApp.DatabaseSetup.getDatabase().agentDao();
     new AsyncTask<Void, Void, Integer>() {
    @Override
    protected Integer doInBackground(Void... params) {
    return agentDao.agentsCount(email, phone, license);
    }

    @Override
    protected void onPostExecute(Integer agentsCount) {
    if (agentsCount > 0) {
    //2: If it already exists then prompt user
    Toast.makeText(Activity.this, "Agent already exists!", Toast.LENGTH_LONG).show();
    }
    else {
    Toast.makeText(Activity.this, "Agent does not exist! Hurray :)", Toast.LENGTH_LONG).show();
    onBackPressed();
    }
    }
    }.execute();

     */

}
