package com.tj.drawwithfrineds;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.tj.drawwithfrineds.UpdateMessage.BitmapUpdateMessage;
import com.tj.drawwithfrineds.UpdateMessage.PencilUpdateMessage;

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
            case BitmapUpdateMessage.INIT_DRAW: {
                // TODO racecase
                ImageView canvas = task.getImageView();

                int[] colorsToDisplay = PaintManager.getInstance().initCurrPicture(canvas);
                Bitmap toDisplay = Bitmap.createBitmap(colorsToDisplay, canvas.getWidth(), canvas.getHeight(), Bitmap.Config.ARGB_8888);
                task.setBitmap(toDisplay);
                task.handleUpdateState(BitmapUpdateMessage.BITMAP_RENDER_COMPLETE);
                break;
            }
            case BitmapUpdateMessage.PENCIL_DRAW: {
                ImageView canvas = task.getImageView();

                int colorsToDisplay[] = PaintManager.getInstance().getCurrPicture();
                PencilUpdateMessage castedTask = (PencilUpdateMessage) task;
                Cord cords[] = castedTask.getCords();
                for (int i = 0; i < cords.length; i++) {
                    int offset = (canvas.getWidth() * cords[i].y) + cords[i].x;
                    if (offset < colorsToDisplay.length && offset >= 0)
                        colorsToDisplay[offset] = 0xffffff00;
                    else
                        System.out.print(offset);
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

                int x = screen.getWidth();
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
