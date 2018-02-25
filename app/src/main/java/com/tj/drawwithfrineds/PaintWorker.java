package com.tj.drawwithfrineds;

import android.graphics.Bitmap;
import android.widget.ImageView;

/**
 * Created by TJ on 2/25/2018.
 */

class PaintWorker implements Runnable {
    private BitmapUpdateMessage task;

    public PaintWorker(BitmapUpdateMessage task) {
        this.task = task;
    }

    @Override
    public void run() {
        switch (task.getTask()) {
            case BitmapUpdateMessage.RANDOM_DRAW:
                ImageView screen = task.getImageView();

                // Moves the current Thread into the background
                android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);

                int[] colorsToDisplay = new int[screen.getWidth() * screen.getHeight()];
                for (int i = 0; i < colorsToDisplay.length; i++) {
                    colorsToDisplay[i] = (int)(0x00ffffff * Math.random());
                    colorsToDisplay[i] = colorsToDisplay[i] << 8;
                }

                Bitmap toDisplay = Bitmap.createBitmap(colorsToDisplay, screen.getWidth(), screen.getHeight(), Bitmap.Config.ARGB_8888);
                task.setBitmap(toDisplay);
                task.handleUpdateState(BitmapUpdateMessage.BITMAP_RENDER_COMPLETE);
                break;
            default:
                break;
        }
    }
}
