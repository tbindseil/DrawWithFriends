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
        // Moves the current Thread into the background
        android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);

        switch (task.getTask()) {
            case BitmapUpdateMessage.PENCIL_DRAW: {
                ImageView canvas = task.getImageView();

                int[] colorsToDisplay = PaintManager.getInstance(canvas).getCurrPicture();
                PencilUpdateMessage castedTask = (PencilUpdateMessage) task;
                Cord cords[] = castedTask.getCords();
                for (int i = 0; i < cords.length; i++) {
                    int offset = (canvas.getWidth() * cords[i].x) + cords[i].y;
                    colorsToDisplay[offset] = 0xffffff00;
                }

                Bitmap toDisplay = Bitmap.createBitmap(colorsToDisplay, canvas.getWidth(), canvas.getHeight(), Bitmap.Config.ARGB_8888);
                task.setBitmap(toDisplay);
                task.handleUpdateState(BitmapUpdateMessage.BITMAP_RENDER_COMPLETE);
                break;
            }
            case BitmapUpdateMessage.RANDOM_DRAW: {
                ImageView screen = task.getImageView();

                int[] colorsToDisplay = new int[screen.getWidth() * screen.getHeight()];
                for (int i = 0; i < colorsToDisplay.length; i++) {
                    colorsToDisplay[i] = (int) (0x00ffffff * Math.random());
                    colorsToDisplay[i] = colorsToDisplay[i] << 8;
                }

                Bitmap toDisplay = Bitmap.createBitmap(colorsToDisplay, screen.getWidth(), screen.getHeight(), Bitmap.Config.ARGB_8888);
                task.setBitmap(toDisplay);
                task.handleUpdateState(BitmapUpdateMessage.BITMAP_RENDER_COMPLETE);
                break;
            }
            default:
                break;
        }
    }
}
