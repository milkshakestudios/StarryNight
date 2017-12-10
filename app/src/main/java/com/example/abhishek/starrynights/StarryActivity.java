package com.example.abhishek.starrynights;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.example.abhishek.starrynights.fragments.DetailFragment;
import com.example.abhishek.starrynights.fragments.ListFragment;
import com.example.abhishek.starrynights.model.StarModel;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StarryActivity extends AppCompatActivity {

    @BindView(R.id.main_act_toolbar)
    Toolbar main_act_toolBar;
    @BindView(R.id.left_pane)
    FrameLayout leftPane;

    @BindView(R.id.right_pane)
    @Nullable
    FrameLayout rightPane;

    private boolean isLargeScreen = false;

    /**
     * Bind the view with ButterKnife and setup the view depending on tablet or phone
     *
     * @param savedInstanceState
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.starry_activity);
        ButterKnife.bind(this);
        initView();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        item.setEnabled(false);
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void showActionBarTitle() {
        getSupportActionBar().setTitle(R.string.stars_out_tonight);
    }


    public void initView() {
        main_act_toolBar.setNavigationIcon(R.drawable.ic_close_white_24dp);
        setSupportActionBar(main_act_toolBar);
        getSupportActionBar().setTitle("");

        //used to track if the screen is large or phone sized
        isLargeScreen = getResources().getBoolean(R.bool.isLargeScreen);
        if (isLargeScreen) {
            getSupportFragmentManager().beginTransaction().add(leftPane.getId(), new ListFragment(), "LEFTPANE").commit();

            Bundle bundle = new Bundle();
            bundle.putString("star", null);
            DetailFragment detailFragment = new DetailFragment();
            detailFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(rightPane.getId(), detailFragment, "RIGHTPANE").commit();
        } else {
            getSupportFragmentManager().beginTransaction().add(leftPane.getId(), new ListFragment(), "LEFTPANE").commit();
        }
    }

    /**
     * open the detail section in a fragment or an activity depending on a phone or a tablet
     *
     * @param model
     */
    public void showDetailSectionForModel(StarModel model) {
        String star = new Gson().toJson(model);
        if (isLargeScreen) {
            Bundle bundle = new Bundle();
            bundle.putString("star", star);
            DetailFragment detailFragment = new DetailFragment();
            detailFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(rightPane.getId(), detailFragment, "RIGHTPANE").commit();
        } else {
            Intent intent = new Intent(this, DetailActivity.class);
            intent.putExtra("star", star);
            startActivity(intent);

        }
    }
}
