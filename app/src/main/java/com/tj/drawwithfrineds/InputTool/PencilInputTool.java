package com.tj.drawwithfrineds.InputTool;

import android.app.Activity;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageView;

import com.tj.drawwithfrineds.R;
import com.tj.drawwithfrineds.ScreenCord;
import com.tj.drawwithfrineds.UpdateMessage.BitmapUpdateMessage;
import com.tj.drawwithfrineds.UpdateMessage.PencilUpdateMessage;
import com.tj.drawwithfrineds.ToolOptionsSlider;

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
    private int color;

    private List<ScreenCord> lastPoints;

    public PencilInputTool(ConstraintLayout configurationLayout, Activity toolSelectActRef) {
        ToolOptionsSlider thicknessBar = toolSelectActRef.findViewById(R.id.thicknessSeekBar);
        thickness = 5;//thicknessBar.getLastSet();

        ToolOptionsSlider colorBar = toolSelectActRef.findViewById(R.id.colorSeekBar);
        int pos = 5;//colorBar.getLastSet();
        float scale = pos / 100f;
        color = 0xff000000 + (int)(scale * 0x00ffffff);

        lastPoints = new ArrayList<>();
    }

    // TODO synchronize touchdown/up first square, pretty much only time when multiple access is possible
    @Override
    public BitmapUpdateMessage[] handleTouch(MotionEvent ev, ImageView canvas) {
        if (ev == null || canvas == null) {
            return null;
        }

        // create bitmap update messages
        int pointerCount = ev.getPointerCount();
        PencilUpdateMessage updates[] = new PencilUpdateMessage[pointerCount];
        for (int i = 0; i < pointerCount; i++) {
            updates[i] = new PencilUpdateMessage(canvas, thickness, color);
        }

        // TODO checkout addBatch

        // multiple events, one per pointer
        for (int i = 0; i < pointerCount; i++) {
            switch (ev.getActionMasked()) {
                case MotionEvent.ACTION_DOWN:
                case MotionEvent.ACTION_POINTER_DOWN:
                    if (i == ev.getActionIndex()) {
                        // save last point
                        if (i < lastPoints.size()) {
                            lastPoints.get(i).x = ev.getX(i);
                            lastPoints.get(i).y = ev.getY(i);
                        }
                        else if (i == lastPoints.size()){
                            lastPoints.add(new ScreenCord(ev.getX(i), ev.getY(i)));
                        }
                        else {
                            Log.e("PencilInputTool", "pointer down order error!");
                        }
                    }
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_POINTER_UP:
                case MotionEvent.ACTION_MOVE:
                    List<ScreenCord> touchPoints = new ArrayList<ScreenCord>();
                    touchPoints.add(new ScreenCord(ev.getX(i), ev.getY(i)));
                    touchPoints.add(lastPoints.get(i));
                    ScreenCord updateLastPoint = new ScreenCord(ev.getX(i), ev.getY(i));
                    lastPoints.set(i, updateLastPoint);
                    updates[i].setCords(touchPoints);

                    break;
                default:
                    updates[i] = null;
                    break;
            }
        }
        return updates;
    }
}
