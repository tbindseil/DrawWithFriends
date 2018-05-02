package com.tj.drawwithfrineds.InputTool;

import android.app.Activity;
import android.support.constraint.ConstraintLayout;
import android.view.MotionEvent;
import android.widget.ImageView;

import com.tj.drawwithfrineds.UpdateMessage.BitmapUpdateMessage;

/**
 * Created by TJ on 3/10/2018.
 */

public class ViewOnlyInputTool extends InputTool {
    public ViewOnlyInputTool(ConstraintLayout configurationLayout, Activity toolSelectActRef) {

    }

    @Override
    public BitmapUpdateMessage[] handleTouch(MotionEvent ev, ImageView canvas) {
        return null;
    }
}
