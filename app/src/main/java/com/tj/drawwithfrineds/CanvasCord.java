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
        this.x = (int)screenCord.x - (int)canvas.getX();
        this.y = (int)screenCord.x - (int)canvas.getY();
    }
}
