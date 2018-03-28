package satel.adsviewer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by satel on 3/25/2018.
 */

public class Ad_DatabaseHandler {

    public static final String AD_TABLE = "Ad_Feed";
    public static final String AD_FAVORITES_TABLE = "Ad_Favorites_Feed";

    private static final String COMMA_SEP = ", ";

    private static final String COLUMN_ID_TYPE = "TEXT PRIMARY KEY";
    private static final String COLUMN_IDIMAGE_TYPE = ",PRIMARY KEY (imageID, imageUrl)";
    private static final String COLUMN_TEXT_TYPE = "TEXT";
    private static final String COLUMN_INT_TYPE = "INT";

    //Maybe necessary to store image in database ?
    private static final String COLUMN_IMAGE_TYPE = "BLOB";

    private static final String COLUMN_AD_IMAGEID = "imageID";
    private static final String COLUMN_AD_IMAGEURL = "imageUrl";
    private static final String COLUMN_AD_PRICE = "price";
    private static final String COLUMN_AD_LOCATION = "location";
    private static final String COLUMN_AD_DESCRIPTION = "description";
    private static final String COLUMN_AD_ISFAVORITED = "isFavorited";

    public static final String SQL_CREATE_AD_TABLE =
            "CREATE TABLE " + AD_TABLE + " (" +
                    COLUMN_AD_IMAGEID  + " " + COLUMN_ID_TYPE + COMMA_SEP +
                    COLUMN_AD_IMAGEURL + " " + COLUMN_TEXT_TYPE + COMMA_SEP +
                    COLUMN_AD_PRICE + " " + COLUMN_TEXT_TYPE + COMMA_SEP +
                    COLUMN_AD_LOCATION + " " + COLUMN_TEXT_TYPE + COMMA_SEP +
                    COLUMN_AD_DESCRIPTION + " " +  COLUMN_TEXT_TYPE + COMMA_SEP +
                    COLUMN_AD_ISFAVORITED + " " + COLUMN_INT_TYPE + COMMA_SEP + ")";

    public static final String SQL_CREATE_AD_FAVORITES_TABLE =
            "CREATE TABLE " + AD_FAVORITES_TABLE + " (" +
                    COLUMN_AD_IMAGEID  + " " + COLUMN_TEXT_TYPE + COMMA_SEP +
                    COLUMN_AD_IMAGEURL + " " + COLUMN_TEXT_TYPE + COMMA_SEP +
                    COLUMN_AD_PRICE + " " + COLUMN_TEXT_TYPE + COMMA_SEP +
                    COLUMN_AD_LOCATION + " " + COLUMN_TEXT_TYPE + COMMA_SEP +
                    COLUMN_AD_DESCRIPTION + " " +  COLUMN_TEXT_TYPE + COMMA_SEP +
                    COLUMN_AD_ISFAVORITED + " " + COLUMN_INT_TYPE +
                    COLUMN_IDIMAGE_TYPE + ");";


    public static final String SQL_DROP_AD_TABLE =
            "DROP TABLE IF EXISTS " + AD_TABLE;
    public static final String SQL_DROP_AD_FAVORITES_TABLE =
            "DROP TABLE IF EXISTS " + AD_FAVORITES_TABLE;


    public static void insertAd(Context context,  Ad_Block ad) {
        DatabaseHelper databaseHelper = new DatabaseHelper(context, null);
        SQLiteDatabase database = databaseHelper.getWritableDatabase();

        ContentValues values = adToContentValues(ad);
        long adID = database.insert(Ad_DatabaseHandler.AD_TABLE, "null", values);

        Log.i(TAG, "Inserted new Ad with ID: " + adID);

        database.close();
    }

    public void insertAdFavorites(Context context, String imageID, String imageUrl) {
        DatabaseHelper databaseHelper = new DatabaseHelper(context, null);
        SQLiteDatabase database = databaseHelper.getWritableDatabase();

        String insertQuery = "UPDATE " + AD_FAVORITES_TABLE + " SET " + COLUMN_AD_ISFAVORITED +
                                "=1" + " WHERE " + COLUMN_AD_IMAGEID + "=" + "'" + imageID + "'" +
                                " AND " + COLUMN_AD_IMAGEURL + "=" + "'" + imageUrl + "'";

        database.execSQL(insertQuery);
        database.close();
    }

    public static List<Ad_Block> getFavoritesList(Context context) {
        List<Ad_Block> favoritesList = new ArrayList<Ad_Block>();
        DatabaseHelper databaseHelper = new DatabaseHelper(context, null);
        SQLiteDatabase database = databaseHelper.getWritableDatabase();

        String getFavoritesQuery = "SELECT * FROM " + AD_TABLE + " WHERE " + COLUMN_AD_IMAGEID +
                " IN ( SELECT " + COLUMN_AD_IMAGEURL + " FROM " + AD_FAVORITES_TABLE + " WHERE " +
                COLUMN_AD_ISFAVORITED + "=1 )";

        Cursor cursor = database.rawQuery(getFavoritesQuery, null);
        Log.i(TAG, "Loading " + cursor.getCount() + " favorited ads...");
        if(cursor.getCount() > 0) {
            cursor.moveToFirst();
            while(!cursor.isAfterLast()) {
                Ad_Block ad = cursorToAd(cursor);
                favoritesList.add(ad);
                cursor.moveToNext();
            }
            Log.i(TAG, "Favorites loaded successfully!");
        }
        else {
            Log.i(TAG, "No favorites to display!");
        }
        database.close();
        return favoritesList;
    }

    public static List<Ad_Block> getAdsList(Context context) {
        DatabaseHelper databaseHelper = new DatabaseHelper(context, null);
        SQLiteDatabase database = databaseHelper.getWritableDatabase();

        Cursor cursor = database.rawQuery("SELECT * FROM " + Ad_DatabaseHandler.AD_TABLE, null);

        Log.i(TAG, "Loading " + cursor.getCount() + " ads...");
        List<Ad_Block> adsList = new ArrayList<Ad_Block>();

        if(cursor.getCount() > 0) {
            cursor.moveToFirst();
            while(!cursor.isAfterLast()) {
                Ad_Block ad = cursorToAd(cursor);
                adsList.add(ad);
                cursor.moveToNext();
            }
            Log.i(TAG, "Ads loaded successfully!");
        }
        else {
            Log.i(TAG, "No ads to load!");
        }
        database.close();
        return adsList;
    }

    private static Ad_Block cursorToAd(Cursor cursor) {
        Ad_Block ad = new Ad_Block();
        ad.setImageID(cursor.getString(cursor.getColumnIndex(COLUMN_AD_IMAGEID)));
        String imageUrl = cursor.getString(cursor.getColumnIndex(COLUMN_AD_IMAGEURL));
        ad.setImageUrl(new imageUrl(imageUrl));
        String price = cursor.getString(cursor.getColumnIndex(COLUMN_AD_PRICE));
        ad.setPriceValue(new Price(Integer.valueOf(price)));
        ad.setLocation(cursor.getString(cursor.getColumnIndex(COLUMN_AD_LOCATION)));
        ad.setDescription(cursor.getString(cursor.getColumnIndex(COLUMN_AD_DESCRIPTION)));
        String isFavorited = cursor.getString(cursor.getColumnIndex(COLUMN_AD_ISFAVORITED));
        ad.setIsFavorited(Boolean.parseBoolean(isFavorited));
        return ad;
    }

    private static ContentValues adToContentValues(Ad_Block ad) {
        ContentValues values = new ContentValues();
        values.put(Ad_DatabaseHandler.COLUMN_AD_IMAGEID, ad.getImageID());
        values.put(Ad_DatabaseHandler.COLUMN_AD_IMAGEURL, ad.getImageUrl());
        values.put(Ad_DatabaseHandler.COLUMN_AD_PRICE, String.valueOf(ad.getPriceValue()));
        values.put(Ad_DatabaseHandler.COLUMN_AD_LOCATION, ad.getLocation());
        values.put(Ad_DatabaseHandler.COLUMN_AD_DESCRIPTION, ad.getDescription());
        values.put(Ad_DatabaseHandler.COLUMN_AD_ISFAVORITED, String.valueOf(ad.getIsFavorited()));
        return values;
    };
}
