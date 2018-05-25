package satel.adsviewer;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.bumptech.glide.Glide;

import java.util.ArrayList;
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
    private List<adBlock> ads;
    private Context context;
    private boolean favoritesView;
    private String appendUrl;


    public AdBlockRecyclerViewAdapter(List<adBlock> ads, Context context, boolean favoritesView) {
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
        final adBlock ad = ads.get(position);

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
                holder.card_favorite_button.setImageResource(R.drawable.favorited);
            }

            /**TODO: Change the appearance of the button once the user has interacted with it
             * This could mean either:
             *  - changing the actual drawable resource being used
             *  - changing the color of the button itself
             */
            holder.card_favorite_button.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {


                    // TODO: Check what color the button should maintain in which view
                    if(!ad.getIsFavorited()) {
                        ad.setIsFavorited(true);
                        holder.card_favorite_button.setImageResource(R.drawable.favorited);
                    }

                    else if(ad.getIsFavorited()) {
                        ad.setIsFavorited(false);
                        holder.card_favorite_button.setImageResource(R.drawable.favorite_button);
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
        public ImageButton card_favorite_button;
        public RelativeLayout card_relative_layout;
        public CardView card_view;


        /**
         * @param v
         */
        ViewHolder(View v) {
            super(v);

            card_title =  v.findViewById(R.id.card_title);
            card_content = v.findViewById(R.id.card_content);
            card_image = v.findViewById(R.id.card_image);
            card_favorite_button = v.findViewById(R.id.favorite_imageButton);
            card_relative_layout = v.findViewById(R.id.card_relative_layout);
            card_view = v.findViewById(R.id.card_view);
        }
    }



}
