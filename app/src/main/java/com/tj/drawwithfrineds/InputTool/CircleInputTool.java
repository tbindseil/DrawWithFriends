package com.tj.drawwithfrineds.InputTool;

import android.graphics.Color;
import android.view.MotionEvent;
import android.widget.ImageView;

import com.tj.drawwithfrineds.ScreenCord;
import com.tj.drawwithfrineds.UpdateMessage.BitmapUpdateMessage;

import java.util.List;

/**
 * Created by TJ on 5/19/2018.
 */

public class CircleInputTool extends InputTool {
    private ScreenCord start;
    private ScreenCord stop;

    private boolean fill = true;
    private int thickness;
    private int color = Color.RED;

    @Override
    public BitmapUpdateMessage[] handleTouch(MotionEvent ev, ImageView canvas) {
        switch (ev.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                this.start = new ScreenCord(ev.getX(), ev.getY());
                break;
            case MotionEvent.ACTION_UP:
                this.start = new ScreenCord(ev.getX(), ev.getY());
                break;
            default:
                return null;
        }
        return new BitmapUpdateMessage[0];
    }
}
