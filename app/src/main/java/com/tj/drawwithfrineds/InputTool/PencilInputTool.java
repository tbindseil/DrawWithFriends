package com.tj.drawwithfrineds.InputTool;

import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageView;

import com.tj.drawwithfrineds.InputTool.InputTool;
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

    public PencilInputTool(int thickness) {
        this.thickness = thickness;
    }

    @Override
    public BitmapUpdateMessage handleTouch(MotionEvent ev, ImageView canvas) {
        if (ev == null || canvas == null) {
            return null;
        }

        // create bitmap update message
        PencilUpdateMessage update = new PencilUpdateMessage(canvas, BitmapUpdateMessage.PENCIL_DRAW, thickness);

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_UP:
                update.setThickness(update.getThickness() * 2);
            case MotionEvent.ACTION_MOVE:
                int historySize = ev.getHistorySize();
                List<ScreenCord> touchPoints = new ArrayList<ScreenCord>();
                touchPoints.add(new ScreenCord(ev.getX(), ev.getY()));

                // questionably needed?
                for (int i = 0; i < historySize; i++) {
                    touchPoints.add(new ScreenCord(ev.getHistoricalX(i), ev.getHistoricalY(i)));
                }

                update.setCords(touchPoints);
                return update;
            default:
                Log.e("PencilInputTool", "action is " + ev.getAction());
                break;
        }

        return null;
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
