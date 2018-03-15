package com.tj.drawwithfrineds.InputTool;

import android.view.MotionEvent;
import android.widget.ImageView;

import com.tj.drawwithfrineds.UpdateMessage.BitmapUpdateMessage;

/**
 * Created by TJ on 3/10/2018.
 */

public abstract class InputTool {

    // note, if you change this
    public static final int VIEW_ONLY = 0;
    public static final int PENCIL = 1;
    public static final int RANDOM = 2;

    public abstract BitmapUpdateMessage handleTouch(MotionEvent ev, ImageView canvas);
}
