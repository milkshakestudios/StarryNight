package com.example.abhishek.starrynights.utils.network;

import com.example.abhishek.starrynights.model.StarList;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Abhishek on 12/9/2017.
 */

public interface ApiStars {
    @GET("list.json")
    public Call<StarList> getStarListJson();
}
