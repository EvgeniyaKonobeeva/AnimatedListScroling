package com.example.ekonobeeva.animatedlistscroling;

import android.graphics.Bitmap;

/**
 * Created by e.konobeeva on 15.11.2016.
 */

public class FoodRestModel {
    private String title;
    private Bitmap bmp;

    public String getTitle() {
        return title;
    }

    public FoodRestModel() {
    }

    public FoodRestModel(String title, Bitmap bmp) {
        this.title = title;
        this.bmp = bmp;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Bitmap getBmp() {
        return bmp;
    }

    public void setBmp(Bitmap bmp) {
        this.bmp = bmp;
    }
}
