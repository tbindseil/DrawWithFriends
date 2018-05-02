package com.tj.drawwithfrineds.InputTool;

import android.app.Activity;
import android.support.constraint.ConstraintLayout;
import android.view.MotionEvent;
import android.widget.ImageView;

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
        if (ev.getActionMasked() == MotionEvent.ACTION_DOWN) {
            BitmapUpdateMessage randomTask = new RandomUpdateMessage(canvas);
            BitmapUpdateMessage[] updates = new BitmapUpdateMessage[1];
            updates[0] = randomTask;
            return updates;
        }
        else {
            return null;
        }
    }
}
