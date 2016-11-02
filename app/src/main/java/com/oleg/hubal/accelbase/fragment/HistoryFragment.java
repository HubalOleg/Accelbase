package com.oleg.hubal.accelbase.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.oleg.hubal.accelbase.R;
import com.oleg.hubal.accelbase.adapter.HistoryAdapter;
import com.oleg.hubal.accelbase.model.Coordinates;

import java.util.ArrayList;
import java.util.TreeMap;

/**
 * Created by User on 01.11.2016.
 */

public class HistoryFragment extends Fragment {

    private TreeMap<String, ArrayList<Coordinates>> mCoordinatesHistory;
    private RecyclerView mRecyclerView;
    private HistoryAdapter mHistoryAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_history, container, false);
        setHasOptionsMenu(true);

        loadData();
        launchRecyclerView();

        return view;
    }

    private void loadData() {
        mCoordinatesHistory = new TreeMap<>();

        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.child(userId).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot historyItem : dataSnapshot.getChildren()) {
                            ArrayList<Coordinates> coordinatesList = new ArrayList<>();

                            for (DataSnapshot coordinateItem : historyItem.getChildren()) {
                                Coordinates coord = coordinateItem.getValue(Coordinates.class);
                                coordinatesList.add(coord);
                            }
                            mCoordinatesHistory.put(historyItem.getKey(), coordinatesList);
                            mHistoryAdapter.notifyData(mCoordinatesHistory);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                }
        );
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_accelerometer:
                AccelerometerFragment accelerometerFragment = new AccelerometerFragment();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.flContainer, accelerometerFragment)
                        .commit();
                return true;
            default:
                return false;
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.history_menu, menu);
    }

    private void launchRecyclerView() {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mHistoryAdapter = new HistoryAdapter(mCoordinatesHistory);
        mRecyclerView.setAdapter(mHistoryAdapter);
    }
}
