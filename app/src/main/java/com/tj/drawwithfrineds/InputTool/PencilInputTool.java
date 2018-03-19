package com.tj.drawwithfrineds.InputTool;

import android.view.MotionEvent;
import android.widget.ImageView;

import com.tj.drawwithfrineds.CanvasCord;
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
    public static final int THICKNESS_1 = 32;
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
        PencilUpdateMessage update = new PencilUpdateMessage(canvas, BitmapUpdateMessage.PENCIL_DRAW);

        final int historySize = ev.getHistorySize();
        List<CanvasCord> touchPoints = new ArrayList<CanvasCord>();

        for (int i = 0; i < historySize; i++) {
            touchPoints.add(new CanvasCord(new ScreenCord(ev.getHistoricalX(i), ev.getHistoricalY(i)), canvas));
            // wow such algo... maybe a hash????
            for (int j = 0 ; j < thickness; j++) {
                for (int k = 0; k < thickness; k++) {
                    int x = touchPoints.get(touchPoints.size() - 1).x;
                    int y = touchPoints.get(touchPoints.size() - 1).y;

                    x = x + j;
                    y = y + k;
                    if (x < canvas.getWidth() && y < canvas.getHeight()) {
                        touchPoints.add(new CanvasCord(x, y));
                    }
                }
            }
        }

        update.setCords(touchPoints);
        return update;
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
