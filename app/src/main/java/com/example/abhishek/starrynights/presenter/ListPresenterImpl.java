package com.example.abhishek.starrynights.presenter;

import android.content.DialogInterface;

import com.example.abhishek.starrynights.adapter.AdapterClickInterface;
import com.example.abhishek.starrynights.adapter.StarRVAdapter;
import com.example.abhishek.starrynights.model.StarList;
import com.example.abhishek.starrynights.model.StarModel;
import com.example.abhishek.starrynights.utils.NetworkUtils;
import com.example.abhishek.starrynights.utils.network.Api;
import com.example.abhishek.starrynights.utils.network.ApiStars;
import com.example.abhishek.starrynights.view.ListFragView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Abhishek on 12/8/2017.
 */

public class ListPresenterImpl implements IListPresenter, AdapterClickInterface {
    public static final String TAG = "List";
    private ListFragView listFragView;

    private StarList starList;
    private StarRVAdapter adapter;

    public ListPresenterImpl(ListFragView listFragView) {
        this.listFragView = listFragView;
    }


    /**
     * init the fragment views and show the loading bar while we fetch for the stars from the api
     */
    @Override
    public void initLoad() {
        listFragView.initView();
        listFragView.showLoadingBar();
        fetchResultsFromApi();
    }

    /**
     * pass the sort value the user selected via the sort option in actionBar
     *
     * @param which
     */
    @Override
    public void sortStarsListBy(int which) {
        if (adapter.sortBy(which)) {
            adapter.notifyDataSetChanged();
        }
    }


    /**
     * check network connection before fetching the Star List from the api, if the user does not have internet then close the app after showing an alertdialog
     * fetch the StarsList from the Api using RetroFit and wait for the response
     * if there was an error, show the user a prompt to close the app
     */
    private void fetchResultsFromApi() {
        if (NetworkUtils.isOnline(listFragView.getContext())) {
            ApiStars apiStars = Api.getInstance().createService(ApiStars.class);
            Call<StarList> call = apiStars.getStarListJson();

            call.enqueue(new Callback<StarList>() {
                @Override
                public void onResponse(Call<StarList> call, Response<StarList> response) {
                    //setup the adapter with the result from the data we fetched from the api
                    starList = response.body();
                    setupAdapter();
                }

                @Override
                public void onFailure(Call<StarList> call, Throwable t) {
                    listFragView.showFailedApiCall();
                }
            });
        } else {
            //show user that internet is required to proceed and close the application
            NetworkUtils.showOfflineDialog(listFragView.getContext(), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    listFragView.closeApp();
                }
            });
        }

    }

    /**
     * hide the loading bar and show the stars list to the user
     */
    private void setupAdapter() {
        adapter = new StarRVAdapter(starList.getList(), listFragView.getContext(), this);
        listFragView.setUpRV(adapter);
        //hide the loading bar
        listFragView.hideLoadingBar();
    }


    /**
     * pass through the item click to the view
     *
     * @param model
     */
    @Override
    public void onItemClicked(StarModel model) {
        listFragView.showDetailSection(model);
    }
}
