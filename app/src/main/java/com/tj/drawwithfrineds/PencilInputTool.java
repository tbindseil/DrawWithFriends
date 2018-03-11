package com.tj.drawwithfrineds;

import android.view.MotionEvent;
import android.widget.ImageView;

/**
 * Created by TJ on 3/10/2018.
 */

public class PencilInputTool extends InputTool {
    @Override
    public BitmapUpdateMessage handleTouch(MotionEvent ev, ImageView canvas) {
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
}
