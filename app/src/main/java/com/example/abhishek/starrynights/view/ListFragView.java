package com.example.abhishek.starrynights.view;

import android.content.Context;

import com.example.abhishek.starrynights.adapter.StarRVAdapter;
import com.example.abhishek.starrynights.model.StarModel;

/**
 * Created by Abhishek on 12/8/2017.
 */

public interface ListFragView {
    void showLoadingBar();

    void initView();

    void hideLoadingBar();

    Context getContext();

    void closeApp();

    void setUpRV(StarRVAdapter adapter);

    void showDetailSection(StarModel model);

    void showFailedApiCall();
}
