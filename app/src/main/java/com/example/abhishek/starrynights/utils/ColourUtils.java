package com.example.abhishek.starrynights.utils;

import android.content.res.ColorStateList;
import android.graphics.Color;

/**
 * Created by Abhishek on 12/8/2017.
 */

public class ColourUtils {

    private static int[][] starStates = new int[][]{
            new int[]{android.R.attr.state_enabled},  // pressed
            new int[]{}  // pressed
    };
    private static int[] starColours = new int[]{
            Color.WHITE,
            Color.TRANSPARENT
    };
    private static ColorStateList starColourStateList = null;


    /**
     * build a colourstate list for the loading stars custom animation
     *
     * @return
     */
    public static ColorStateList getStarColourStateList() {
        if (starColourStateList == null) {
            starColourStateList = new ColorStateList(starStates, starColours);
        }
        return starColourStateList;
    }

}
