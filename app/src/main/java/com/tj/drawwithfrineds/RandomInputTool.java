package com.tj.drawwithfrineds;

import android.view.MotionEvent;
import android.widget.ImageView;

/**
 * Created by TJ on 3/12/2018.
 */

public class RandomInputTool extends InputTool {
    @Override
    public BitmapUpdateMessage handleTouch(MotionEvent ev, ImageView canvas) {
        BitmapUpdateMessage randomTask = new RandomUpdateMessage(canvas, BitmapUpdateMessage.RANDOM_DRAW);
        return randomTask;
    }
}
