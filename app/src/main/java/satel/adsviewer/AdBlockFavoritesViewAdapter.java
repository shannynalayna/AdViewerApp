package satel.adsviewer;

import android.content.Context;
import android.support.annotation.NonNull;
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

/**
 * Created by satel on 3/28/2018.
 */

public class AdBlockFavoritesViewAdapter extends RecyclerView.Adapter<AdBlockFavoritesViewAdapter.ViewHolder>{

    private List<Ad_Block> ads;
    private Context context;
    private boolean favoriteView;

    public AdBlockFavoritesViewAdapter(List<Ad_Block> ads, Context context, boolean favoriteView) {
        this.ads = ads;
        this.context = context;
        this.favoriteView = favoriteView;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ad_card, parent, false);
        ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final AdBlockFavoritesViewAdapter.ViewHolder holder, int position) {
        final Ad_Block ad = ads.get(position);

        try {
            Glide.with(context).load(ad.getImageUrl()).placeholder(R.drawable.no_image_available)
                    .into(holder.fav_card_image);
            holder.fav_card_content.setText(ad.getContent());
            holder.fav_card_title.setText(ad.getDescription());
            holder.card_remove_check.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    CheckBox cb = (CheckBox) v;
                    if(holder.card_remove_check.isChecked()) {
                        if(ad.getIsFavorited()) {
                            ad.setIsFavorited(false);
                            holder.card_remove_check.setText(R.string.addFavorite);
                            holder.card_remove_check.setChecked(false);
                        }
                        else {
                            ad.setIsFavorited(true);
                            holder.card_remove_check.setText(R.string.removeFavorite);
                            holder.card_remove_check.setChecked(false);
                        }
                    }

                }
            });

        } catch (Exception e) {
            Log.e("Ad Block Adapter", "Error with loading card ");
        }
    }

    @Override
    public int getItemCount() {
        return ads.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView fav_card_title;
        public TextView fav_card_content;
        public ImageView fav_card_image;
        public CheckBox card_remove_check;
        public RelativeLayout fav_card_relative_layout;
        public CardView fav_card_view;

        public ViewHolder(View v) {
            super(v);

            fav_card_title = (TextView) v.findViewById(R.id.card_title);
            fav_card_content = (TextView) v.findViewById(R.id.card_content);
            fav_card_image = (ImageView) v.findViewById(R.id.card_image);
            card_remove_check = (CheckBox) v.findViewById(R.id.favoriteCheck);
            card_remove_check.setText(R.string.removeFavorite);
            fav_card_relative_layout = (RelativeLayout) v.findViewById(R.id.card_relative_layout);
            fav_card_view = (CardView) v.findViewById(R.id.card_view);


        }
    }
}
