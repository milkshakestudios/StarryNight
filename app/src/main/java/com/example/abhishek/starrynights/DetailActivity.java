package com.example.abhishek.starrynights;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.example.abhishek.starrynights.fragments.DetailFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Abhishek on 12/9/2017.
 * Activity shell to house the Details Fragment when opened from a phone
 * otherwise open the fragment directly when opening on tablet
 */

public class DetailActivity extends AppCompatActivity {

    @BindView(R.id.holder_detail_act)
    FrameLayout holder_detail_act;
    @BindView(R.id.detail_act_toolbar)
    Toolbar detail_act_toolbar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_act_layout);
        ButterKnife.bind(this);
        setSupportActionBar(detail_act_toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle bundle = new Bundle();
        bundle.putString("star", getIntent().getStringExtra("star"));

        DetailFragment detailFragment = new DetailFragment();
        detailFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(holder_detail_act.getId(), detailFragment, "DETAIL_FRAG").commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    /**
     * Sets the actionBar title
     *
     * @param title
     */
    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }
}
