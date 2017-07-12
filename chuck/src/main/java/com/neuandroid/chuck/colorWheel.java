package com.neuandroid.chuck;

import android.graphics.Color;

import java.util.Random;

/**
 * Created by mac on 13/07/2017.
 */

public class colorWheel {

    private String[] colors = {

            "#39add1", // light blue
            "#3079ab", // dark blue
            "#c25975", // mauve
            "#e15258", // red
            "#f9845b", // orange
            "#838cc7", // lavender
            "#7d669e", // purple
            "#53bbb4", // aqua
            "#51b46d", // green
            "#e0ab18", // mustard
            "#637a91", // dark gray
            "#f092b0", // pink
            "#b7c0c7", // light gray
            "#FFFF00", // Yellow
            "#FF00FF", // Magenta / Fuchsia
            "#800000", // Maroon
            "#808000", // Olive
            "#FF6347", // tomato
            "#FFA07A", // light salmon
            "#7CFC00", // awn green
            "#87CEEB", // sky blue
            "#FFE4C4", // bisque
            "#CD853F", // peru
            "#F5FFFA", // mint cream
            "#F5F5F5", // white smoke
            "#C71585", // medium violet red
//            "#000000", // black
            "#ffffff", // white

    };

    public int getColor(){

        String color;
        Random random = new Random();

        int randoNumber = random.nextInt(colors.length);

        color = colors[randoNumber];
        int colorAsInt = Color.parseColor(color);

        return colorAsInt;
    }
}
