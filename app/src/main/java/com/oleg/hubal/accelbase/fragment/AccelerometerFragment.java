package com.oleg.hubal.accelbase.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.oleg.hubal.accelbase.Constants;
import com.oleg.hubal.accelbase.R;
import com.oleg.hubal.accelbase.Utils;
import com.oleg.hubal.accelbase.activity.SignupActivity;
import com.oleg.hubal.accelbase.service.AccelerometerService;

/**
 * Created by User on 01.11.2016.
 */

public class AccelerometerFragment extends Fragment
        implements CompoundButton.OnCheckedChangeListener {

    private CheckBox chbStopStart;
    private EditText etDelay;

    private boolean checkBoxState = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_accelerometer, container, false);
        setHasOptionsMenu(true);

        if (savedInstanceState != null) {
            checkBoxState = savedInstanceState.getBoolean(Constants.STATE_CHECKED);
        }

        etDelay = (EditText) view.findViewById(R.id.etDelay);
        chbStopStart = (CheckBox) view.findViewById(R.id.chbStopStartService);
        chbStopStart.setChecked(checkBoxState);
        chbStopStart.setOnCheckedChangeListener(this);

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(Constants.STATE_CHECKED, chbStopStart.isChecked());
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        Intent intent = new Intent(getContext(), AccelerometerService.class);
        if (isChecked) {
            long delay = Utils.getDelayFromEditText(etDelay.getText());
            intent.putExtra(Constants.EXTRA_EDIT_TEXT_DELAY, delay);
            getActivity().startService(intent);
        } else {
            getActivity().stopService(intent);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_sign_out:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getContext(), SignupActivity.class));
                return true;
            case R.id.menu_history:
                HistoryFragment historyFragment = new HistoryFragment();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().
                        replace(R.id.flContainer, historyFragment).
                        commit();
            default:
                return false;
        }
    }
}
