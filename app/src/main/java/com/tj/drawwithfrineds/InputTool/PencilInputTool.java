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
    public static final int THICKNESS_THIN = 32;
    public static final int THICKNESS_MEDIUM = 64;
    public static final int THICKNESS_THICK = 128;
    private int thickness;

    private List<ScreenCord> lastPoints;

    public PencilInputTool(ConstraintLayout configurationLayout, Activity toolSelectActRef) {
        RadioGroup thicknessGroup = (RadioGroup)toolSelectActRef.findViewById(R.id.thickness_group);
        int thicknessChecked = thicknessGroup.getCheckedRadioButtonId();
        switch (thicknessChecked) {
            case R.id.thinButton:
                thickness = THICKNESS_THIN;
                break;
            case R.id.mediumButton:
                thickness = THICKNESS_MEDIUM;
                break;
            case R.id.thickButton:
                thickness = THICKNESS_THICK;
                break;
            default:
                thickness = THICKNESS_MEDIUM;
                break;
        }

        lastPoints = new ArrayList<ScreenCord>();
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
                        Log.e("tag", "i is " + i);
                        updates[i].setThickness(thickness * 2);
                    }
                case MotionEvent.ACTION_MOVE:
                    List<ScreenCord> touchPoints = new ArrayList<ScreenCord>();
                    touchPoints.add(new ScreenCord(ev.getX(i), ev.getY(i)));
                    for (int j = 0; j < ev.getHistorySize(); j++) {
                        touchPoints.add(new ScreenCord(ev.getHistoricalX(i, j), ev.getHistoricalY(i, j)));
                    }
                    updates[i].setCords(touchPoints);
                    Log.e("PencilInputTool", "i is " + i + " and touchPoints.size() is " + touchPoints.size() + " and action is " + ev.getActionMasked());
                    break;
                default:
                    Log.e("PencilInputTool", "action is " + ev.getAction());
                    updates[i] = null;
                    break;
            }
        }
        return updates;
    }
}
