package mcs.salazar.jesus.firstappforandroid.container;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import mcs.salazar.jesus.firstappforandroid.R;
import mcs.salazar.jesus.firstappforandroid.Season;

/**
 * Created by jesussalazar on 4/30/18.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.SeasonViewHolder> {

    private ArrayList<Season> dataSet;
    // Try to avoid having pointers to Contexts
    Context mContext;

    public RecyclerViewAdapter(Context context, ArrayList<Season> list) {
        mContext = context;
        dataSet = list;
    }



    @Override
    public SeasonViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater
                .from(viewGroup.getContext())
                .inflate(R.layout.recyclerview_layout_item,
                        viewGroup,
                        false);
        SeasonViewHolder svh = new SeasonViewHolder(v);
        return svh;
    }

    @Override
    public void onBindViewHolder(SeasonViewHolder personViewHolder, int position) {
        Season currentSeason = dataSet.get(position);
        personViewHolder.seasonNumber.setText(
                "Season " + currentSeason.getNumberOfSeason());
        personViewHolder.remainingEpisodes.setText(
                (currentSeason.getEpisodes() - currentSeason.getWatchedEpisodes()) +
                        " remaining."
        );
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public static class SeasonViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView seasonNumber;
        TextView remainingEpisodes;
        ImageView statusBar;

        SeasonViewHolder(View itemView) {
            super(itemView);
            cv = itemView.findViewById(R.id.card_view);
            seasonNumber = itemView.findViewById(R.id.firstLine);
            remainingEpisodes = itemView.findViewById(R.id.secondLine);
            statusBar = itemView.findViewById(R.id.status_bar);
        }
    }

}
