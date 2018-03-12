package com.tj.drawwithfrineds;

import android.view.MotionEvent;
import android.widget.ImageView;

/**
 * Created by TJ on 3/10/2018.
 */

public class PencilInputTool extends InputTool {
    @Override
    public BitmapUpdateMessage handleTouch(MotionEvent ev, ImageView canvas) {
        //debug
        if (ev == null) {
            return getTUpdate(canvas);
        }

        // create bitmap update message
        PencilUpdateMessage update = new PencilUpdateMessage(canvas, BitmapUpdateMessage.PENCIL_DRAW);

        final int historySize = ev.getHistorySize();
        int xCords[] = new int[historySize];
        int yCords[] = new int[historySize];

        for (int i = 0; i < historySize; i++) {
            xCords[i] = (int)ev.getHistoricalX(i);
            yCords[i] = (int)ev.getHistoricalY(i);
        }

        update.setCords(xCords, yCords);
        return update;
    }

    private BitmapUpdateMessage getTUpdate(ImageView canvas) {
        int xCords[] = new int[canvas.getHeight() + canvas.getWidth()];
        int yCords[] = new int[canvas.getHeight() + canvas.getWidth()];

        for (int i = 0; i < xCords.length; i++) {
            if (i < canvas.getWidth()) {
                yCords[i] = canvas.getHeight() - 1;
                xCords[i] = i;
            }
            else {
                yCords[i] = i - canvas.getHeight();
                xCords[i] = canvas.getWidth() / 2;
            }
        }
        PencilUpdateMessage update = new PencilUpdateMessage(canvas, BitmapUpdateMessage.PENCIL_DRAW);
        update.setCords(xCords, yCords);
        return update;
    }
}
