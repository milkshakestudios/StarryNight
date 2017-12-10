package com.example.abhishek.starrynights.fragments;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.abhishek.starrynights.R;
import com.example.abhishek.starrynights.StarryActivity;
import com.example.abhishek.starrynights.adapter.StarRVAdapter;
import com.example.abhishek.starrynights.model.StarModel;
import com.example.abhishek.starrynights.presenter.IListPresenter;
import com.example.abhishek.starrynights.presenter.ListPresenterImpl;
import com.example.abhishek.starrynights.utils.ColourUtils;
import com.example.abhishek.starrynights.view.ListFragView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Abhishek on 12/8/2017.
 */

public class ListFragment extends Fragment implements ListFragView {

    //ButterKnife used for view binding
    @BindView(R.id.star_1)
    ImageView star1;
    @BindView(R.id.star_2)
    ImageView star2;
    @BindView(R.id.star_3)
    ImageView star3;
    @BindView(R.id.loading_layout)
    View loadingLayout;

    @BindView(R.id.star_rv)
    RecyclerView recyclerView;


    //custom animation for animating stars below the progress bar
    //wanted to remove the progressbar but did not know if the requirements allowed for that
    //so kept my custom loading bar as well as default progressBar
    int cycleCounter = 0;
    private IListPresenter presenter = null;
    private Runnable starLoader = new Runnable() {
        @Override
        public void run() {
            if (cycleCounter > 5) {
                cycleCounter = 0;
            }
            switch (cycleCounter) {
                case 0:
                    star1.setEnabled(false);
                    star2.setEnabled(false);
                    star3.setEnabled(false);
                    break;
                case 1:
                    star1.setEnabled(true);
                    break;
                case 2:
                    star1.setEnabled(true);
                    star2.setEnabled(true);
                    break;
                case 3:
                    star1.setEnabled(true);
                    star2.setEnabled(true);
                    star3.setEnabled(true);
                    break;
                case 4:
                    star1.setEnabled(false);
                    star2.setEnabled(true);
                    star3.setEnabled(true);
                    break;
                case 5:
                    star1.setEnabled(false);
                    star2.setEnabled(false);
                    star3.setEnabled(true);
                    break;
            }
            cycleCounter++;
            star1.postDelayed(this, 450L);
        }
    };


    /**
     * init the presenter
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        presenter = new ListPresenterImpl(this);
    }


    /**
     * Add the sort option in the ActionBar
     *
     * @param menu
     * @param inflater
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.act_main_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


    /**
     * Show use the sorting options when he invokes the Sort button in the actionBar
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        item.setEnabled(false);
        switch (item.getItemId()) {
            case R.id.sort_opt:
                showSortDialog();
                item.setEnabled(true);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    /**
     * Show options for sort and pass use click to presenter
     */
    private void showSortDialog() {
        new AlertDialog.Builder(getContext(), R.style.customDialog)
                .setTitle("Sort by:")
                .setItems(R.array.sort_opts, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        presenter.sortStarsListBy(which);
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_holder_layout, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.initLoad();
    }


    /**
     * Show the loader and start the custom loading stars animation
     */
    @Override
    public void showLoadingBar() {
        loadingLayout.setVisibility(View.VISIBLE);
        star1.postDelayed(starLoader, 450L);
    }


    /**
     * init the imageView for stars with the colourStateList to the states can be changed to control the colour
     * setup the recylerview with a gridlayout and control the columns based on the device size
     */
    @Override
    public void initView() {
        star1.setImageTintList(ColourUtils.getStarColourStateList());
        star2.setImageTintList(ColourUtils.getStarColourStateList());
        star3.setImageTintList(ColourUtils.getStarColourStateList());
        int span = 0;
        if (getResources().getBoolean(R.bool.isLargeScreen)) {
            span = 1;
        } else {
            span = 2;
        }
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), span));
        recyclerView.setVisibility(View.GONE);
    }

    /**
     * perform a ciruclar reveal to show the list of Stars
     * added a delay because the loading from the api was too fast
     * and made the view look jumpy
     */
    @Override
    public void hideLoadingBar() {
        loadingLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                circularHide(loadingLayout);
//                loadingLayout.setVisibility(View.GONE);
                star1.removeCallbacks(starLoader);
            }
        }, 500L);

    }


    /**
     * simple circularHide anim
     *
     * @param view
     */
    private void circularHide(View view) {
        final View viewToAnimate = view;

        int cx = viewToAnimate.getWidth() / 2;
        int cy = viewToAnimate.getHeight() / 2;

        float initialRadius = (float) Math.hypot(cx, cy);

        Animator anim = ViewAnimationUtils.createCircularReveal(viewToAnimate, cx, cy, initialRadius, 0);

        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                viewToAnimate.setVisibility(View.GONE);
                ((StarryActivity) getActivity()).showActionBarTitle();
            }
        });
        //set duration because it was oddly animating depending on screen size
        anim.setDuration(450L);

        anim.start();
    }


    /**
     * The finish method has to be invoked when user has no internet connection - called from ListPresenterImpl
     */
    @Override
    public void closeApp() {
        getActivity().finish();
    }


    /**
     * add the adapter to the recycler view
     *
     * @param adapter
     */
    @Override
    public void setUpRV(StarRVAdapter adapter) {
        recyclerView.setAdapter(adapter);
        recyclerView.setVisibility(View.VISIBLE);

    }


    /**
     * passed the item click from the adapter to the activity so it can invoke the right behaviour based on tablet or phone
     *
     * @param model
     */
    @Override
    public void showDetailSection(StarModel model) {
        ((StarryActivity) getActivity()).showDetailSectionForModel(model);
    }

    @Override
    public void showFailedApiCall() {
        new AlertDialog.Builder(getContext(), R.style.customDialog)
                .setCancelable(false)
                .setTitle(R.string.end_point_error)
                .setMessage(R.string.cloud_endpoint_description)
                .setPositiveButton(R.string.btn_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        closeApp();
                    }
                }).show();
    }

}
