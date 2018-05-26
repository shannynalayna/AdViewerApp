package satel.adsviewer;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;

import java.util.List;


/**
 * Adapter for the Recycler View
 * Intended to translate the data from a single adBlock to the views included
 * in the card view:
 *  - image view
 *  - text view (for ad description)
 *  - text view (for ad price / location )
 *  - checkbox (to add / remove ad from list of favorites)
 */
public class adBlockRecyclerViewAdapter extends RecyclerView.Adapter
                                                    <adBlockRecyclerViewAdapter.ViewHolder> {

    private final List<adBlock> ads;
    private final Context context;
    private final String appendUrl;

    /**
     * @param ads list of loaded ads
     * @param context application context
     */
    adBlockRecyclerViewAdapter(List<adBlock> ads, Context context) {
        this.ads = ads;
        this.context = context;
        appendUrl = context.getString(R.string.appendUrl);
    }

    /**
     * @param parent parent of the current view
     * @param viewType type of view
     * @return holder
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card, parent, false);
        return new ViewHolder(v);
    }

    /**
     * @param holder view holder
     * @param position position of the ad
     */
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final adBlock ad = ads.get(position);

        try {
            /*
              Placeholder image used as url may be null
             */
            Glide.with(context).load(appendUrl + ad.getImageUrl())
                    .placeholder(R.drawable.no_image_available).into(holder.card_image);
            holder.card_content.setText(ad.getContent());
            holder.card_title.setText(ad.getDescription());

            /*
              Ensuring the checkbox maintains it's checked state if the ad is
              favorited
             */
            if(ad.getIsFavorited()) {
                holder.card_favorite_button.setImageResource(R.drawable.favorited);
            }

            holder.card_favorite_button.setOnClickListener(new View.OnClickListener() {


                public void onClick(View v) {

                    //TODO: Is it necessary to separate out this from the view adapter ?
                    if(!ad.getIsFavorited()) {
                        adLogic.adInteraction(ad, true);
                        holder.card_favorite_button.setImageResource(R.drawable.favorited);
                    }

                    else if(ad.getIsFavorited()) {
                        adLogic.adInteraction(ad, false);
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
        final TextView card_title;
        final TextView card_content;
        final ImageView card_image;
        final ImageButton card_favorite_button;

        /**
         * @param v view
         */
        ViewHolder(View v) {
            super(v);

            card_title = v.findViewById(R.id.card_title);
            card_content = v.findViewById(R.id.card_content);
            card_image = v.findViewById(R.id.card_image);
            card_favorite_button = v.findViewById(R.id.favorite_imageButton);
        }
    }



}
