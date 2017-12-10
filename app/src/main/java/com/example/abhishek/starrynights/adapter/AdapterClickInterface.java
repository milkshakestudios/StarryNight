package com.example.abhishek.starrynights.adapter;

import com.example.abhishek.starrynights.model.StarModel;

/**
 * Created by Abhishek on 12/9/2017.
 * This interface is used to pass through the selection from the adapter to the presenter
 */

public interface AdapterClickInterface {
    void onItemClicked(StarModel model);
}
