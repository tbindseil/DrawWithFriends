package com.tj.drawwithfrineds;

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
        this.x = (int)screenCord.x;
        this.y = (int)screenCord.y;
    }

    public double distanceSquaredFrom(CanvasCord otherCord) {
        return Math.pow(otherCord.x - x, 2) +Math.pow(otherCord.y - y, 2);
    }

    public double distanceSquaredFromOrigin() {
        return Math.pow(x, 2) + Math.pow(y, 2);
    }
}
