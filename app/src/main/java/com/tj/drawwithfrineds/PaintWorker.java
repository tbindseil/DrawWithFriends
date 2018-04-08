package com.tj.drawwithfrineds;

import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;

import com.tj.drawwithfrineds.UpdateMessage.BitmapUpdateMessage;
import com.tj.drawwithfrineds.UpdateMessage.PencilUpdateMessage;

import java.util.ArrayList;
import java.util.List;

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
        android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_DEFAULT);

        ImageView canvas = task.getImageView();
        int[] colorsToDisplay;
        if (task.getTask() == BitmapUpdateMessage.INIT_DRAW) {
            colorsToDisplay = PaintManager.getInstance().initCurrPicture(canvas);
        }
        else {
            colorsToDisplay = PaintManager.getInstance().getCurrPicture();
        }
        
        switch (task.getTask()) {
            case BitmapUpdateMessage.PENCIL_DRAW: {
                PencilUpdateMessage castedTask = (PencilUpdateMessage) task;

                // convert touch point to a pixel
                List<CanvasCord> canvasCords = new ArrayList<CanvasCord>();
                ScreenCord screenCord = castedTask.getScreenCord();
                int thickness = castedTask.getThickness();

                canvasCords.add(new CanvasCord(screenCord, canvas));
                int x = canvasCords.get(canvasCords.size() - 1).x;
                int y = canvasCords.get(canvasCords.size() - 1).y;
                // wow such algo... maybe a hash????
                thickness = thickness / 2;
                for (int j = 0 - thickness; j < thickness; j++) {
                    for (int k = 0 - thickness; k < thickness; k++) {
                        int currX = x + j;
                        int currY = y + k;
                        if (currX < canvas.getWidth() && currY < canvas.getHeight()) {
                            canvasCords.add(new CanvasCord(currX, currY));
                        }
                    }
                }

                for (int i = 0; i < canvasCords.size(); i++) {
                    //Log.d("PencilDrawTask", "i is " + i + " x is " + canvasCords.get(i).x + " y is " + canvasCords.get(i).y);
                    int offset = (canvas.getWidth() * canvasCords.get(i).y) + canvasCords.get(i).x;
                    if (offset < colorsToDisplay.length && offset >= 0)
                        // TODO colors
                        colorsToDisplay[offset] = 0xffff0000;
                    else
                        Log.e("PencilDrawTask", "Bad cord, x is " + canvasCords.get(i).x +
                                " and y is " + canvasCords.get(i).y + " and offset is " + offset);
                }
                break;
            }
            case BitmapUpdateMessage.RANDOM_DRAW: {
                for (int i = 0; i < colorsToDisplay.length; i++) {
                    colorsToDisplay[i] = (int) (0x00ffffff * Math.random());
                    colorsToDisplay[i] = colorsToDisplay[i] | 0xff000000;
                }
                break;
            }
            case BitmapUpdateMessage.QUADRANT_DRAW: {
                for (int i = 0; i < canvas.getHeight() / 2; i++) {
                    for (int j = 0; j < canvas.getWidth() / 2; j++) {
                        int offset = canvas.getWidth() * i + j;
                        if (offset >= 0 && offset < colorsToDisplay.length) {
                            colorsToDisplay[offset] = 0xffffff00;
                        } else {
                            Log.e("PencilDrawTask", "Bad cord, x is " + j +
                                    " and y is " + i + " and offset is " + offset);
                        }
                    }
                }
                break;
            }
            case BitmapUpdateMessage.INIT_DRAW:
            default:
                break;
        }
        // save picture
        PaintManager.getInstance().setCurrPicture(colorsToDisplay);

        Bitmap toDisplay = Bitmap.createBitmap(colorsToDisplay, canvas.getWidth(), canvas.getHeight(), Bitmap.Config.ARGB_8888);
        task.setBitmap(toDisplay);
        task.handleUpdateState(BitmapUpdateMessage.BITMAP_RENDER_COMPLETE);
    }
}
