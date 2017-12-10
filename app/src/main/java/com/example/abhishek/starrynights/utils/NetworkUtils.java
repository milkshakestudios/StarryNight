package com.example.abhishek.starrynights.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;

import com.example.abhishek.starrynights.R;

/**
 * Created by Abhishek on 12/9/2017.
 */
public class NetworkUtils {

    /**
     * check if the app isOnline using the provided context
     *
     * @param context
     * @return
     */
    public static boolean isOnline(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }


    /**
     * Show the user a offline dialog and use the provided click listener to trigger the action based on input
     *
     * @param context
     * @param onClickListener
     */
    public static void showOfflineDialog(Context context, DialogInterface.OnClickListener onClickListener) {
        new AlertDialog.Builder(context, R.style.customDialog)
                .setCancelable(false)
                .setTitle(R.string.internet_required)
                .setMessage(R.string.please_connect_to_internet)
                .setPositiveButton(R.string.btn_ok, onClickListener).show();
    }
}

