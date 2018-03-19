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
        Log.d("CanvasCord", "Incoming screenCord.x is " + screenCord.x + " and screenCord.y is " + screenCord.y);
        this.x = (int)screenCord.x - (int)canvas.getX();
        this.y = (int)screenCord.y - (int)canvas.getY();
        Log.d("CanvasCord", "this.x is " + this.x + " and this.y is " + this.y);
    }
}
