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

                // convert touch point to a pixel
                List<CanvasCord> canvasCords = new ArrayList<CanvasCord>();
                ScreenCord screenCord = castedTask.getScreenCord();
                int thickness = castedTask.getThickness();

                canvasCords.add(new CanvasCord(screenCord, canvas));
                int x = canvasCords.get(canvasCords.size() - 1).x;
                int y = canvasCords.get(canvasCords.size() - 1).y;
                // wow such algo... maybe a hash????
                for (int j = 0; j < thickness; j++) {
                    for (int k = 0; k < thickness; k++) {
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

                // save picture
                PaintManager.getInstance().setCurrPicture(colorsToDisplay);

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
                    colorsToDisplay[i] = colorsToDisplay[i] | 0xff000000;
                }

                // save picture
                PaintManager.getInstance().setCurrPicture(colorsToDisplay);

                Bitmap toDisplay = Bitmap.createBitmap(colorsToDisplay, screen.getWidth(), screen.getHeight(), Bitmap.Config.ARGB_8888);
                task.setBitmap(toDisplay);
                task.handleUpdateState(BitmapUpdateMessage.BITMAP_RENDER_COMPLETE);
                break;
            }
            case BitmapUpdateMessage.QUADRANT_DRAW: {
                ImageView screen = task.getImageView();

                int[] colorsToDisplay = PaintManager.getInstance().getCurrPicture();
                for (int i = 0; i < screen.getHeight() / 2; i++) {
                    for (int j = 0; j < screen.getWidth() / 2; j++) {
                        int offset = screen.getWidth() * i + j;
                        if (offset >= 0 && offset < colorsToDisplay.length) {
                            colorsToDisplay[offset] = 0xffffff00;
                        } else {
                            Log.e("PencilDrawTask", "Bad cord, x is " + j +
                                    " and y is " + i + " and offset is " + offset);
                        }
                    }
                }

                // save picture
                PaintManager.getInstance().setCurrPicture(colorsToDisplay);

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
