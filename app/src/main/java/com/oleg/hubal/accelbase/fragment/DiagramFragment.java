package com.oleg.hubal.accelbase.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;
import com.androidplot.xy.XYStepMode;
import com.oleg.hubal.accelbase.R;
import com.oleg.hubal.accelbase.model.Coordinates;
import com.oleg.hubal.accelbase.utils.Constants;
import com.oleg.hubal.accelbase.utils.GraphXLabelFormat;
import com.oleg.hubal.accelbase.utils.Utils;

import java.util.ArrayList;

/**
 * Created by User on 02.11.2016.
 */

public class DiagramFragment extends Fragment {

    private XYPlot mPlot;
    private String[] domainValues;
    private ArrayList<Double> xCoord, yCoord, zCoord;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_diagram, container, false);

        getCoordinatesData();

        mPlot = (XYPlot) view.findViewById(R.id.diagram_plot);
        mPlot.setDomainStep(XYStepMode.INCREMENT_BY_VAL, 1 + domainValues.length / 7);
        mPlot.setDomainValueFormat(new GraphXLabelFormat(domainValues));

        createXYZSeries();

        return view;
    }

    private void getCoordinatesData() {
        ArrayList<Coordinates> coordinates =
                getArguments().getParcelableArrayList(Constants.BUNDLE_COORDINATES_LIST);
        ArrayList<String> date = new ArrayList<>();

        xCoord = new ArrayList<>();
        yCoord = new ArrayList<>();
        zCoord = new ArrayList<>();

        for (Coordinates coord : coordinates) {
            date.add(Utils.formatDate(coord.getDate(), "mm:ss"));
            xCoord.add(coord.getCoordinateX());
            yCoord.add(coord.getCoordinateY());
            zCoord.add(coord.getCoordinateZ());
        }

        domainValues = date.toArray(new String[date.size()]);
    }

    private void createXYZSeries() {
        XYSeries xSeries = new SimpleXYSeries(xCoord,
                SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, getString(R.string.X));
        XYSeries ySeries = new SimpleXYSeries(yCoord,
                SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, getString(R.string.Y));
        XYSeries zSeries = new SimpleXYSeries(zCoord,
                SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, getString(R.string.Z));

        mPlot.addSeries(xSeries, new LineAndPointFormatter(Color.GREEN, Color.GREEN, null, null));
        mPlot.addSeries(ySeries, new LineAndPointFormatter(Color.RED, Color.RED, null, null));
        mPlot.addSeries(zSeries, new LineAndPointFormatter(Color.BLUE, Color.BLUE, null, null));
    }
}
