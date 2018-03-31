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


/**
 * Adapter for the Recycler View
 * Intended to translate the data from a single Ad_Block to the views included
 * in the card view:
 *  - image view
 *  - text view (for ad description)
 *  - text view (for ad price / location )
 *  - checkbox (to add / remove ad from list of favorites)
 */
public class AdBlockRecyclerViewAdapter extends RecyclerView.Adapter
                                                    <AdBlockRecyclerViewAdapter.ViewHolder> {

    private List<Ad_Block> ads;
    private Context context;
    private boolean favoritesView;
    private String appendUrl;

    /**
     * @param ads
     * @param context
     * @param favoritesView
     */
    public AdBlockRecyclerViewAdapter(List<Ad_Block> ads, Context context, boolean favoritesView) {
        this.ads = ads;
        this.context = context;
        this.favoritesView = favoritesView;
        appendUrl = context.getString(R.string.appendUrl);
    }

    /**
     * @param parent
     * @param viewType
     * @return holder
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ad_card, parent, false);
        ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    /**
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Ad_Block ad = ads.get(position);

        try {
            /**
             * Placeholder image used as url may be null
             */
            Glide.with(context).load(appendUrl + ad.getImageUrl())
                    .placeholder(R.drawable.no_image_available).into(holder.card_image);
            holder.card_content.setText(ad.getContent());
            holder.card_title.setText(ad.getDescription());

            /**
             * Ensuring the checkbox maintains it's checked state if the ad is
             * favorited
             */
            if(ad.getIsFavorited()) {
                holder.card_favorite_check.setText(R.string.removeFavorite);
                holder.card_favorite_check.setChecked(true);
            }

            holder.card_favorite_check.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    CheckBox cb = (CheckBox) v;

                    if(holder.card_favorite_check.isChecked() && !ad.getIsFavorited()) {
                        ad.setIsFavorited(true);
                        holder.card_favorite_check.setText(R.string.removeFavorite);
                        holder.card_favorite_check.setChecked(true);
                    }

                    else if(!holder.card_favorite_check.isChecked() && ad.getIsFavorited()) {
                        ad.setIsFavorited(false);
                        holder.card_favorite_check.setText(R.string.addFavorite);
                        holder.card_favorite_check.setChecked(false);
                    }

                }
            });

        } catch (Exception e) {
            Log.e("Adapter", "Error Displaying Ad");
        }

    }

    /**
     * @return ads.size()
     */
    @Override
    public int getItemCount() {
        return ads.size();
    }


    protected class ViewHolder extends RecyclerView.ViewHolder {

        //Defining View objects
        public TextView card_title;
        public TextView card_content;
        public ImageView card_image;
        public CheckBox card_favorite_check;
        public RelativeLayout card_relative_layout;
        public CardView card_view;


        /**
         * @param v
         */
        ViewHolder(View v) {
            super(v);

            card_title = (TextView) v.findViewById(R.id.card_title);
            card_content = (TextView) v.findViewById(R.id.card_content);
            card_image = (ImageView) v.findViewById(R.id.card_image);
            card_favorite_check = (CheckBox) v.findViewById(R.id.favoriteCheck);
            card_relative_layout = (RelativeLayout) v.findViewById(R.id.card_relative_layout);
            card_view = (CardView) v.findViewById(R.id.card_view);
        }
    }



}
