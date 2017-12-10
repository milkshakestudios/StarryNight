package com.example.abhishek.starrynights.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Abhishek on 12/9/2017.
 */

public class StarList {
    @SerializedName("List")
    @Expose
    private java.util.List<StarModel> list = null;

    @Override
    public String toString() {
        return "StarList{" +
                "list=" + list.toString() +
                '}';
    }

    public java.util.List<StarModel> getList() {
        return list;
    }

    public void setList(java.util.List<StarModel> list) {
        this.list = list;
    }


}
