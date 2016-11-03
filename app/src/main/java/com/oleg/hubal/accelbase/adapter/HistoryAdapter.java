package com.oleg.hubal.accelbase.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.oleg.hubal.accelbase.R;
import com.oleg.hubal.accelbase.listener.OnHistoryItemClickListener;
import com.oleg.hubal.accelbase.model.Coordinates;
import com.oleg.hubal.accelbase.utils.Utils;

import java.util.ArrayList;
import java.util.TreeMap;

/**
 * Created by User on 01.11.2016.
 */

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder>
        implements View.OnClickListener {

    private TreeMap<String, ArrayList<Coordinates>> mHistoryMap;
    private OnHistoryItemClickListener mItemClickListener;
    private ArrayList<String> mDateKeys;

    public HistoryAdapter(OnHistoryItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
        mHistoryMap = new TreeMap<>();
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
        StringBuilder coordinatesInfo = new StringBuilder();
        String dateKey = mDateKeys.get(position);

        ArrayList<Coordinates> coordinatesList = mHistoryMap.get(dateKey);

        for (Coordinates coordinates : coordinatesList) {
            String date = Utils.formatDate(coordinates.getDate(), "hh:mm:ss");

            coordinatesInfo.append(date).append("\n")
                    .append("X: ").append(coordinates.getCoordinateX()).append("\n")
                    .append("Y: ").append(coordinates.getCoordinateY()).append("\n")
                    .append("Z: ").append(coordinates.getCoordinateZ()).append("\n");
        }

        holder.tvCoords.setText(coordinatesInfo.toString().trim());
        holder.tvDate.setText(Utils.formatDate(dateKey));
        holder.view.setTag(dateKey);
        holder.view.setOnClickListener(this);
    }

    @Override
    public int getItemCount() {
        return mHistoryMap.size();
    }

    public void notifyDataChange(TreeMap<String, ArrayList<Coordinates>> historyMap) {
        mHistoryMap = historyMap;
        mDateKeys = new ArrayList<>(mHistoryMap.keySet());
        notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        mItemClickListener.onHistoryItemClick((String) v.getTag());
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
