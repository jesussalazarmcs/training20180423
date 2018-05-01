package mcs.salazar.jesus.firstappforandroid.container;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import mcs.salazar.jesus.firstappforandroid.R;
import mcs.salazar.jesus.firstappforandroid.model.Season;

/**
 * Created by jesussalazar on 4/30/18.
 */

public class ListViewAdapter extends ArrayAdapter<Season> {
    private ArrayList<Season> dataSet;
    // Try to avoid having pointers to Contexts
    Context mContext;

    public ListViewAdapter(Context context, ArrayList<Season> list) {
        super(context, 0 , list);
        mContext = context;
        dataSet = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater
                    .from(mContext)
                    .inflate(R.layout.listview_layout_item,
                            parent,
                            false);

        Season currentSeason = dataSet.get(position);

        ImageView image = listItem.findViewById(R.id.status_bar);
        // image.setImageResource(currentMovie.getmImageDrawable());

        TextView firstLine = listItem.findViewById(R.id.firstLine);
        firstLine.setText("Season " + currentSeason.getNumberOfSeason());

        TextView secondLine = listItem.findViewById(R.id.firstLine);
        secondLine.setText((currentSeason.getEpisodes() - currentSeason.getWatchedEpisodes()) +
        " remaining.");

        return listItem;
    }

}
