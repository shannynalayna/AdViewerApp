package satel.adsviewer;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by satel on 3/25/2018.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";
    private static final String DATABASE_NAME = "main.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context, SQLiteDatabase.CursorFactory factory) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.i(TAG, "Creating Database: [" + DATABASE_NAME + " v." + DATABASE_VERSION + "]");

        sqLiteDatabase.execSQL(Ad_DatabaseHandler.SQL_CREATE_AD_TABLE);
        sqLiteDatabase.execSQL(Ad_DatabaseHandler.SQL_CREATE_AD_FAVORITES_TABLE);
        sqLiteDatabase.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        Log.i(TAG, "Upgrading Database: [From" + DATABASE_NAME + " v." + DATABASE_VERSION +
                            "to " + DATABASE_NAME + " v." + newVersion + "]");
        sqLiteDatabase.execSQL(Ad_DatabaseHandler.SQL_DROP_AD_TABLE);
        sqLiteDatabase.execSQL(Ad_DatabaseHandler.SQL_DROP_AD_FAVORITES_TABLE);
        onCreate(sqLiteDatabase);
    }


}
