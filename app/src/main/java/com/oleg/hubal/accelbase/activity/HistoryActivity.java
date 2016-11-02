package com.oleg.hubal.accelbase.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.oleg.hubal.accelbase.Constants;
import com.oleg.hubal.accelbase.R;
import com.oleg.hubal.accelbase.fragment.DiagramFragment;
import com.oleg.hubal.accelbase.fragment.HistoryFragment;
import com.oleg.hubal.accelbase.listener.OnHistoryItemClickListener;
import com.oleg.hubal.accelbase.model.Coordinates;

import java.util.ArrayList;

/**
 * Created by User on 02.11.2016.
 */

public class HistoryActivity extends AppCompatActivity implements OnHistoryItemClickListener {

    private DiagramFragment mDiagramFrag;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        if (savedInstanceState == null) {
            Fragment historyFrag = new HistoryFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.flFragmentContainer, historyFrag).commit();
        }

        mDiagramFrag = null;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_accelerometer:
                startActivity(new Intent(this, MainActivity.class));
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.history_menu, menu);
        return true;
    }

    @Override
    public void showDiagram(ArrayList<Coordinates> coordinates) {
        FrameLayout container = (FrameLayout) findViewById(R.id.flDiagramContainerAH);
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(Constants.BUNDLE_COORDINATE_LIST, coordinates);
        if (container == null) {
            Intent intent = new Intent(this, DiagramActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        } else {
            DiagramFragment diagramFrag = new DiagramFragment();
            diagramFrag.setArguments(bundle);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(container.getId(), diagramFrag).commit();
        }
    }
}
