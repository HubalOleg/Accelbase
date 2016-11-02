package com.oleg.hubal.accelbase.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.oleg.hubal.accelbase.R;
import com.oleg.hubal.accelbase.Utils;
import com.oleg.hubal.accelbase.model.Coordinates;

import java.util.ArrayList;
import java.util.TreeMap;

/**
 * Created by User on 01.11.2016.
 */

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private TreeMap<String, ArrayList<Coordinates>> mHistoryMap;
    private ArrayList<String> mDateKeys;

    public HistoryAdapter(TreeMap<String, ArrayList<Coordinates>> historyMap) {
        mHistoryMap = historyMap;
        mDateKeys = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.card_history, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String dateKey = mDateKeys.get(position);
        StringBuilder coordInfo = new StringBuilder();

        ArrayList<Coordinates> coordinatesList = mHistoryMap.get(dateKey);
        for (Coordinates coordinates : coordinatesList) {
            String date = Utils.formatDate(coordinates.getDate());

            coordInfo.append(date + "\n")
                    .append("X: " + coordinates.getCoordinateX() + "\n")
                    .append("Y: " + coordinates.getCoordinateY() + "\n")
                    .append("Z: " + coordinates.getCoordinateZ() + "\n");
        }

        holder.tvCoords.setText(coordInfo.toString().trim());
        holder.tvDate.setText(Utils.formatDate(dateKey));
    }

    @Override
    public int getItemCount() {
        return mHistoryMap.size();
    }

    public void notifyData(TreeMap<String, ArrayList<Coordinates>> historyMap) {
        mHistoryMap = historyMap;
        mDateKeys = new ArrayList<>(mHistoryMap.keySet());
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvDate, tvCoords;

        ViewHolder(View itemView) {
            super(itemView);
            tvDate = (TextView) itemView.findViewById(R.id.tvHistoryDate);
            tvCoords = (TextView) itemView.findViewById(R.id.tvHistoryCoords);
        }
    }
}
