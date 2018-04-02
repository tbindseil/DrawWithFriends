package com.tj.drawwithfrineds.InputTool;

import android.view.MotionEvent;
import android.widget.ImageView;

import com.tj.drawwithfrineds.UpdateMessage.BitmapUpdateMessage;
import com.tj.drawwithfrineds.UpdateMessage.QuadrantUpdateMessage;

/**
 * Created by TJ on 3/18/2018.
 */

public class QuadrantInputTool extends InputTool {
    @Override
    public BitmapUpdateMessage[] handleTouch(MotionEvent ev, ImageView canvas) {
        QuadrantUpdateMessage updates[] = new QuadrantUpdateMessage[1];
        updates[0] = new QuadrantUpdateMessage(canvas, BitmapUpdateMessage.QUADRANT_DRAW);
        return updates;
    }
}
