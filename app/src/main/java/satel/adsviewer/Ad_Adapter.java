package satel.adsviewer;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Filterable;

import java.util.List;

/**
 * Created by satel on 3/26/2018.
 */

public class Ad_Adapter extends ArrayAdapter<Ad_Block> implements Filterable {

    public List<Ad_Block> ads;
    private Context context;

    public Ad_Adapter(@NonNull Context context, int resource, @NonNull List<Ad_Block> ads) {
        super(context, R.layout.ad_activity_layout, ads);
        this.ads = ads;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Ad_Block getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return null;
    }
}
