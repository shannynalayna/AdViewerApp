package satel.adsviewer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

public class AdBlockRecyclerViewAdapter extends RecyclerView.Adapter<AdBlockRecyclerViewAdapter.ViewHolder> {

    private List<Ad_Block> ads;
    private Context context;

    public AdBlockRecyclerViewAdapter(List<Ad_Block> ads, Context context) {
        this.ads = ads;
        this.context = context;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ad_card, parent, false);
        ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Ad_Block ad = ads.get(position);
        Log.i("AdBlock Adapter", "In the onBindViewHolder function");
        Log.i("AdBlock Adapter", "At this URL: " + context);
        try {
            Picasso.with(context).load(ad.getImageUrl()).into(holder.card_image);
            holder.card_title.setText(ad.getDescription());
            holder.card_content.setText(ad.getContent());

        } catch (Exception e) {
            Log.e("AdBlock Adapter", "Error with creating Drawable with " + e.toString());
        }

        try {
            holder.card_title.toString();
        } catch (Exception e) {
            Log.e("ad adapter", "Caught");
        }

    }

    @Override
    public int getItemCount() {
        return ads.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        //Defining View objects
        public TextView card_title;
        public TextView card_content;
        public ImageView card_image;
        public LinearLayout cardLinearLayout;
        public LinearLayout textLinearLayout;


        public ViewHolder(View v) {
            super(v);

            Log.i("AdBlockAdapter", "In the View Holder item");

            card_title = (TextView) v.findViewById(R.id.card_title);
            card_content = (TextView) v.findViewById(R.id.card_content);
            card_image = (ImageView) v.findViewById(R.id.card_image);
            cardLinearLayout = (LinearLayout) v.findViewById(R.id.card_linearLayout);
            textLinearLayout = (LinearLayout) v.findViewById(R.id.card_text_linearLayout);
        }
    }



}
