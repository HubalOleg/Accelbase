package com.oleg.hubal.accelbase.adapter;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.oleg.hubal.accelbase.R;
import com.oleg.hubal.accelbase.Utils;
import com.oleg.hubal.accelbase.listener.OnHistoryItemClickListener;
import com.oleg.hubal.accelbase.model.Coordinates;

import java.util.ArrayList;
import java.util.TreeMap;

/**
 * Created by User on 01.11.2016.
 */

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder>
        implements View.OnClickListener {

    private TreeMap<String, ArrayList<Coordinates>> mHistoryMap;
    private ArrayList<String> mDateKeys;
    private OnHistoryItemClickListener mItemClickListener;

    public HistoryAdapter(TreeMap<String, ArrayList<Coordinates>> historyMap,
                          OnHistoryItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
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
        StringBuilder coordInfo = new StringBuilder();
        String dateKey = mDateKeys.get(position);
        Bundle bundle = new Bundle();

        ArrayList<Coordinates> coordinatesList = mHistoryMap.get(dateKey);
        for (Coordinates coordinates : coordinatesList) {
            String date = Utils.formatDate(coordinates.getDate(), "hh:mm:ss");
            double x = coordinates.getCoordinateX();
            double y = coordinates.getCoordinateY();
            double z = coordinates.getCoordinateZ();

            coordInfo.append(date + "\n")
                    .append("X: " + x + "\n")
                    .append("Y: " + y + "\n")
                    .append("Z: " + z + "\n");
        }

        holder.tvCoords.setText(coordInfo.toString().trim());
        holder.tvDate.setText(Utils.formatDate(dateKey));
        holder.view.setTag(coordinatesList);
        holder.view.setOnClickListener(this);
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

    @Override
    public void onClick(View v) {
        mItemClickListener.showDiagram((ArrayList<Coordinates>) v.getTag());
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvDate, tvCoords;
        public View view;

        ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            tvDate = (TextView) itemView.findViewById(R.id.tvHistoryDate);
            tvCoords = (TextView) itemView.findViewById(R.id.tvHistoryCoords);
        }
    }
}
