package com.tj.drawwithfrineds.InputTool;

import android.view.MotionEvent;
import android.widget.ImageView;

import com.tj.drawwithfrineds.InputTool.InputTool;
import com.tj.drawwithfrineds.UpdateMessage.BitmapUpdateMessage;

/**
 * Created by TJ on 3/10/2018.
 */

public class ViewOnlyInputTool extends InputTool {
    @Override
    public BitmapUpdateMessage[] handleTouch(MotionEvent ev, ImageView canvas) {
        // TODO handle this better
        return null;
    }
}
