package com.tj.drawwithfrineds.InputTool;

import android.app.Activity;
import android.support.constraint.ConstraintLayout;
import android.view.MotionEvent;
import android.widget.ImageView;

import com.tj.drawwithfrineds.InputTool.InputTool;
import com.tj.drawwithfrineds.UpdateMessage.BitmapUpdateMessage;
import com.tj.drawwithfrineds.UpdateMessage.RandomUpdateMessage;

/**
 * Created by TJ on 3/12/2018.
 */

public class RandomInputTool extends InputTool {
    public RandomInputTool(ConstraintLayout configurationLayout, Activity toolSelectActRef) {

    }

    @Override
    public BitmapUpdateMessage[] handleTouch(MotionEvent ev, ImageView canvas) {
        BitmapUpdateMessage randomTask = new RandomUpdateMessage(canvas, BitmapUpdateMessage.RANDOM_DRAW);
        BitmapUpdateMessage[] updates = new BitmapUpdateMessage[1];
        updates[0] = randomTask;
        return updates;
    }
}
