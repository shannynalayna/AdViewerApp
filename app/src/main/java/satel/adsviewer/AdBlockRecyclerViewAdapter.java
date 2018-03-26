package satel.adsviewer;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import satel.adsviewer.AdBlockFragment.OnListFragmentInteractionListener;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Ad_Block} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 */
public class AdBlockRecyclerViewAdapter extends RecyclerView.Adapter<AdBlockRecyclerViewAdapter.ViewHolder> {

    private final List<Ad_Block> mValues;
    private final OnListFragmentInteractionListener mListener;

    public AdBlockRecyclerViewAdapter(List<Ad_Block> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_adblock, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    //TODO: Look at android setup for here and down
    //Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        // Get element from the dataset at this position
        // Replace the contents of the view with that element
        holder.mItem = mValues.get(position);
        holder.mTitleView.setText(mValues.get(position).getDescription());
        holder.mContentView.setText(mValues.get(position).getContent());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }


    /**
     * This class provides a reference to the views for each Ad_Block item
     * More complex items may need more than one view per item, and this
     * provides access to all the views for a data item in a view holder
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        //Each view item will have a text view and an image view

        public final View mView;

        public final TextView mTitleView;
        public final TextView mContentView;
        public final ImageView mImageView;

        public Ad_Block mItem;

        public ViewHolder(View view) {
            //
            super(view);
            mView = view;

            mTitleView = (TextView) view.findViewById(R.id.card_title);
            mContentView = (TextView) view.findViewById(R.id.card_content);
            mImageView = (ImageView) view.findViewById(R.id.card_image);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
