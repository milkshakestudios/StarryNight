package com.example.abhishek.starrynights.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Abhishek on 12/9/2017.
 */

public class StarModel {
    @SerializedName("ID")
    @Expose
    private String iD;
    @SerializedName("FirstName")
    @Expose
    private String firstName;
    @SerializedName("LastName")
    @Expose
    private String lastName;
    @SerializedName("Portrait")
    @Expose
    private String portrait;
    @SerializedName("BadgeColor")
    @Expose
    private String badgeColor;
    @SerializedName("description")
    @Expose
    private String description;

    public String getID() {
        return iD;
    }

    public void setID(String iD) {
        this.iD = iD;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public String getBadgeColor() {
        return badgeColor;
    }

    public void setBadgeColor(String badgeColor) {
        this.badgeColor = badgeColor;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "StarModel{" +
                "iD='" + iD + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", portrait='" + portrait + '\'' +
                ", badgeColor='" + badgeColor + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
