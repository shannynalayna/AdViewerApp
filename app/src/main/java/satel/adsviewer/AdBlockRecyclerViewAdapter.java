package satel.adsviewer;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.bumptech.glide.Glide;

import java.util.List;

public class AdBlockRecyclerViewAdapter extends RecyclerView.Adapter<AdBlockRecyclerViewAdapter.ViewHolder> {

    private List<Ad_Block> ads;
    private Context context;
    private boolean favoriteView;
    private String appendUrl = "https://images.finncdn.no/dynamic/480x360c/";
    private int adCount;


    public AdBlockRecyclerViewAdapter(List<Ad_Block> ads, Context context, boolean favoriteView, int adCount) {
        this.ads = ads;
        this.context = context;
        this.favoriteView = favoriteView;
        this.adCount = adCount;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ad_card, parent, false);
        ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Ad_Block ad = ads.get(position);
        Log.i("AdBlock Adapter", "In the onBindViewHolder function");
        Log.i("AdBlock Adapter", "At this URL: " + ad.getImageUrl());
        try {
            Glide.with(context).load(appendUrl + ad.getImageUrl().getUrl()).placeholder(R.drawable.no_image_available).into(holder.card_image);
            holder.card_content.setText(ad.getContent());
            holder.card_title.setText(ad.getDescription());

            if(ad.getIsFavorited()) {
                holder.card_favorite_check.setText(R.string.removeFavorite);
            }

            holder.card_favorite_check.setHapticFeedbackEnabled(true);
            holder.card_favorite_check.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    CheckBox cb = (CheckBox) v;

                    if(holder.card_favorite_check.isChecked()) {
                        if(ad.getIsFavorited()) {
                            ad.setIsFavorited(false);
                            holder.card_favorite_check.setText(R.string.addFavorite);
                            holder.card_favorite_check.setChecked(false);
                        }
                        else {
                            ad.setIsFavorited(true);
                            holder.card_favorite_check.setText(R.string.removeFavorite);
                            holder.card_favorite_check.setChecked(false);
                        }
                    }
                }
            });


        } catch (Exception e) {
            Log.e("AdBlock Adapter", "Error with creating Drawable with " + e.toString());
        }
    }


    @Override
    public int getItemCount() {
        return adCount;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        //Defining View objects
        public TextView card_title;
        public TextView card_content;
        public ImageView card_image;
        public CheckBox card_favorite_check;
        public RelativeLayout card_relative_layout;
        public CardView card_view;


        public ViewHolder(View v) {
            super(v);

            Log.i("AdBlockAdapter", "In the View Holder item");

            card_title = (TextView) v.findViewById(R.id.card_title);
            card_content = (TextView) v.findViewById(R.id.card_content);
            card_image = (ImageView) v.findViewById(R.id.card_image);
            card_favorite_check = (CheckBox) v.findViewById(R.id.favoriteCheck);

            if(favoriteView) {
                card_favorite_check.setText(R.string.removeFavorite);
            }

            card_relative_layout = (RelativeLayout) v.findViewById(R.id.card_relative_layout);
            card_view = (CardView) v.findViewById(R.id.card_view);
        }
    }



}
