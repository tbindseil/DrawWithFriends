package com.tj.drawwithfrineds;

import android.util.Log;
import android.widget.ImageView;

/**
 * Created by TJ on 3/11/2018.
 */

public class CanvasCord {
    public int x,y;

    public CanvasCord(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public CanvasCord(ScreenCord screenCord, ImageView canvas) {
        //Log.d("CanvasCord", "Incoming screenCord.x is " + screenCord.x + " and screenCord.y is " + screenCord.y);
        this.x = (int)screenCord.x;
        this.y = (int)screenCord.y;
        //Log.d("CanvasCord", "this.x is " + this.x + " and this.y is " + this.y);
    }

    public double distanceSquaredFrom(CanvasCord otherCord) {
        return Math.pow(otherCord.x - x, 2) +Math.pow(otherCord.y - y, 2);
    }

    public double distanceSquaredFromOrigin() {
        return Math.pow(x, 2) + Math.pow(y, 2);
    }
    public boolean equals(CanvasCord otherCord) {
        return (otherCord.x == x && otherCord.y == y) ? true : false;
    }
}
