package satel.adsviewer;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;

/**
 * Created by satel on 3/25/2018.
 */

public class DisplayImageTask extends AsyncTask<String, Void, Bitmap> {
    ImageView bmImage;

    public DisplayImageTask(ImageView bmImage) {
        this.bmImage = bmImage;
    }

    protected Bitmap doInBackground(String... urls) {
        String url = urls[0];
        Bitmap ad_image = null;
        try {
            InputStream in = new java.net.URL(url).openStream();
            ad_image = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("DisplayImageTask Error", e.getMessage());
        }
        return ad_image;
    }

    protected void onPostExecute(Bitmap result) {
        bmImage.setImageBitmap(result);
    }
}
