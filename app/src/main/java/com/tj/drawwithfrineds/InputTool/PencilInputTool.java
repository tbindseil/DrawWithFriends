package com.tj.drawwithfrineds.InputTool;

import android.view.MotionEvent;
import android.widget.ImageView;

import com.tj.drawwithfrineds.CanvasCord;
import com.tj.drawwithfrineds.InputTool.InputTool;
import com.tj.drawwithfrineds.ScreenCord;
import com.tj.drawwithfrineds.UpdateMessage.BitmapUpdateMessage;
import com.tj.drawwithfrineds.UpdateMessage.PencilUpdateMessage;

/**
 * Created by TJ on 3/10/2018.
 */

public class PencilInputTool extends InputTool {
    @Override
    public BitmapUpdateMessage handleTouch(MotionEvent ev, ImageView canvas) {
        if (ev == null || canvas == null) {
            return null;
        }

        // create bitmap update message
        PencilUpdateMessage update = new PencilUpdateMessage(canvas, BitmapUpdateMessage.PENCIL_DRAW);

        final int historySize = ev.getHistorySize();
        CanvasCord[] touchPoints = new CanvasCord[historySize];

        for (int i = 0; i < historySize; i++) {
            touchPoints[i] = new CanvasCord(new ScreenCord(ev.getHistoricalX(i), ev.getHistoricalY(i)), canvas);
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
