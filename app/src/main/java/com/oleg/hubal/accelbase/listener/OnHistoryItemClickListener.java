package com.oleg.hubal.accelbase.listener;

import com.oleg.hubal.accelbase.model.Coordinates;

import java.util.ArrayList;

/**
 * Created by User on 02.11.2016.
 */

public interface OnHistoryItemClickListener {
    public void showDiagram(ArrayList<Coordinates> coordinates);
}
