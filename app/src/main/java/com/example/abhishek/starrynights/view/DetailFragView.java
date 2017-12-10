package com.example.abhishek.starrynights.view;

/**
 * Created by Abhishek on 12/9/2017.
 */

public interface DetailFragView {
    void hideWaitingForStarsText();

    void setId(String id);

    void setFirstNameTV(String firstNameTV);

    void setLastNameTV(String lastNameTV);

    void setPortraitIV(String portraitIV);

    void setBadgeColour(String badgeColor);

    void setDescription(String description);

    void setBornTV(String born);

    void setOccupationTV(String occupation);
}
