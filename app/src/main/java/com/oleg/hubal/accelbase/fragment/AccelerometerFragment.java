package com.oleg.hubal.accelbase.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.oleg.hubal.accelbase.R;
import com.oleg.hubal.accelbase.service.AccelerometerService;

/**
 * Created by User on 01.11.2016.
 */

public class AccelerometerFragment extends Fragment
        implements CompoundButton.OnCheckedChangeListener {

    private CheckBox chbStopStart;
    private Intent intent;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_accelerometer, container, false);

        chbStopStart = (CheckBox) v.findViewById(R.id.chbStopStartService);
        chbStopStart.setOnCheckedChangeListener(this);

        intent = new Intent(getContext(), AccelerometerService.class);

        return v;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            getActivity().startService(intent);
        } else {
            getActivity().stopService(intent);
        }
    }
}
