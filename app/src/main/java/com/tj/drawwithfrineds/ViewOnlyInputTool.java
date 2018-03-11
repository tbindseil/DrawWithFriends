package com.tj.drawwithfrineds;

import android.view.MotionEvent;
import android.widget.ImageView;

/**
 * Created by TJ on 3/10/2018.
 */

public class ViewOnlyInputTool extends InputTool {
    @Override
    public BitmapUpdateMessage handleTouch(MotionEvent ev, ImageView canvas) {
        // TODO handle this better
        return null;
    }
}
