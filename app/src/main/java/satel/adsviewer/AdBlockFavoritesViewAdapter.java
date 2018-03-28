package satel.adsviewer;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * Created by satel on 3/28/2018.
 */

public class AdBlockFavoritesViewAdapter extends RecyclerView.Adapter<AdBlockRecyclerViewAdapter.ViewHolder>{

    Ad_Block[] ads;



    @NonNull
    @Override
    public AdBlockRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull AdBlockRecyclerViewAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
