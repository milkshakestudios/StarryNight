package com.example.abhishek.starrynights.utils.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Abhishek on 12/9/2017.
 */

public class Api {
    public static final String BASE_URL = "https://s3.amazonaws.com/sapi.aminheidari.com/tmp/";

    private static Api instance = null;


    /**
     * singleton for the api instance
     *
     * @return
     */
    public static Api getInstance() {
        if (instance == null) {
            instance = new Api();
        }
        return instance;
    }

    private Retrofit getRetroFit() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }


    /**
     * Return a RetroFit service based on the given class name
     *
     * @param serviceClass
     * @param <S>
     * @return
     */
    public <S> S createService(Class<S> serviceClass) {
        return getRetroFit().create(serviceClass);
    }
}
