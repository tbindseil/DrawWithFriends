package com.tj.drawwithfrineds.InputTool;

import android.app.Activity;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.RadioGroup;

import com.tj.drawwithfrineds.InputTool.InputTool;
import com.tj.drawwithfrineds.R;
import com.tj.drawwithfrineds.ScreenCord;
import com.tj.drawwithfrineds.UpdateMessage.BitmapUpdateMessage;
import com.tj.drawwithfrineds.UpdateMessage.PencilUpdateMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TJ on 3/10/2018.
 */

public class PencilInputTool extends InputTool {
    public static final int THICKNESS_1 = 128;
    private int thickness;

    public PencilInputTool(ConstraintLayout configurationLayout, Activity toolSelectActRef) {
        /* TODO RadioGroup thicknessGroup = (RadioGroup)toolSelectActRef.findViewById(R.id.thickness_group);
        thickness = thicknessGroup.getCheckedRadioButtonId();*/
    }

    public PencilInputTool(int thickness) {
        this.thickness = thickness;
    }

    private boolean pointerIndexDownOrUp(MotionEvent ev, int i) {
        int action = ev.getAction();
        int actionPointerIndex = (action & MotionEvent.ACTION_POINTER_INDEX_MASK) >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
        int actionPointerIndexOfI = ev.getPointerId(i);
        if (actionPointerIndex == actionPointerIndexOfI) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public BitmapUpdateMessage[] handleTouch(MotionEvent ev, ImageView canvas) {
        if (ev == null || canvas == null) {
            return null;
        }

        // create bitmap update messages
        int pointerCount = ev.getPointerCount();
        PencilUpdateMessage updates[] = new PencilUpdateMessage[pointerCount];
        for (int i = 0; i < pointerCount; i++) {
            updates[i] = new PencilUpdateMessage(canvas, BitmapUpdateMessage.PENCIL_DRAW, thickness);
        }

        // TODO checkout addBatch

        // multiple events, one per pointer
        for (int i = 0; i < pointerCount; i++) {
            switch (ev.getActionMasked()) {
                case MotionEvent.ACTION_DOWN:
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_POINTER_DOWN:
                case MotionEvent.ACTION_POINTER_UP:
                    if (i == ev.getActionIndex()) { //pointerIndexDownOrUp(ev, i)) {
                        updates[i].setThickness(thickness * 2);
                    }
                case MotionEvent.ACTION_MOVE:
                    ScreenCord touchPoint = new ScreenCord(ev.getX(i), ev.getY(i));
                    updates[i].setCord(touchPoint);
                    break;
                default:
                    Log.e("PencilInputTool", "action is " + ev.getAction());
                    updates[i] = null;
                    break;
            }
        }
        return updates;
    }

/*debug
    private BitmapUpdateMessage getTUpdate(ImageView canvas) {
        int xCords[] = new int[canvas.getHeight() + canvas.getWidth()];
        int yCords[] = new int[canvas.getHeight() + canvas.getWidth()];

        for (int i = 0; i < xCords.length; i++) {
            if (i < canvas.getWidth()) {
                yCords[i] = canvas.getHeight() / 2;
                xCords[i] = i;
            }
            else {
                yCords[i] = i - canvas.getWidth();
                xCords[i] = canvas.getWidth() / 2;
            }
        }
        PencilUpdateMessage update = new PencilUpdateMessage(canvas, BitmapUpdateMessage.PENCIL_DRAW);
        update.setCords(xCords, yCords);
        return update;
    }*/
}
