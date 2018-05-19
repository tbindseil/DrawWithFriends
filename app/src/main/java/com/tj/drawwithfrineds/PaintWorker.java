package com.tj.drawwithfrineds;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.tj.drawwithfrineds.UpdateMessage.BitmapUpdateMessage;
import com.tj.drawwithfrineds.UpdateMessage.PencilUpdateMessage;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
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

    private void connectTheDots(CanvasCord c1, CanvasCord c2, List<CanvasCord> toRet) {
        // going from c1 to c2,
        // check (c1.x, c1.y + 1) vs (c1.x + 1, c1.y)
        // whichever is closer to c2 is the new c1 and gets added to toRet
        Log.e("recursive", "c1.x, y is " + c1.x + ", " + c1.y + " and c2.x y is " + c2.x + ", " + c2.y);
        int stepMagnitude = PaintManager.getInstance().getStepMagnitude();
        int xStep = (c1.x < c2.x) ? stepMagnitude : 0 - stepMagnitude;
        int yStep = (c1.y < c2.y) ? stepMagnitude : 0 - stepMagnitude;

        double c2DistFromOrigin = c2.distanceSquaredFromOrigin();
        boolean lessThanLater = c1.distanceSquaredFromOrigin() < c2DistFromOrigin ?
                false : true;

        CanvasCord last = c1;
        CanvasCord opt1;
        CanvasCord opt2;
        do {
            opt1 = new CanvasCord(last.x, last.y + yStep);
            opt2 = new CanvasCord(last.x + xStep, last.y);

            if (lessThanLater) {
                if (opt1.distanceSquaredFromOrigin() < c2DistFromOrigin || opt2.distanceSquaredFromOrigin() < c2DistFromOrigin) {
                    break;
                }
            } else {
                if (opt1.distanceSquaredFromOrigin() > c2DistFromOrigin || opt2.distanceSquaredFromOrigin() > c2DistFromOrigin) {
                    break;
                }
            }

            double dist1 = opt1.distanceSquaredFrom(c2);
            double dist2 = opt2.distanceSquaredFrom(c2);

            if (dist1 < dist2) {
                last = opt1;
            } else {
                last = opt2;
            }

            toRet.add(last);
        } while (true);
            //} while (!opt1.equals(c2) && !opt2.equals(c2));
        Log.e("recursive", "points in this message is " + toRet.size());
    }

    private void connectTheDotsRecursive(CanvasCord c1, CanvasCord c2, List<CanvasCord> toRet) {
        //Log.e("recursive", "c1.x, y is " + c1.x + ", " + c1.y + " and c2.x y is " + c2.x + ", " + c2.y);
        if (Math.abs(c1.x - c2.x) <= 1 && Math.abs(c1.y - c2.y) <= 1) {
            //Log.e("recursive", "return!");
            return;
        }

        int midx = c1.x > c2.x ? (((c1.x - c2.x) / 2) + c2.x) : (((c2.x - c1.x) / 2) + c1.x);
        int midy = c1.y > c2.y ? (((c1.y - c2.y) / 2) + c2.y) : (((c2.y - c1.y) / 2) + c1.y);
        CanvasCord mid = new CanvasCord(midx, midy);
        toRet.add(mid);
        Log.e("recursive", "mid.x y is " + mid.x + ", " + mid.y);
        connectTheDotsRecursive(mid, c2, toRet);
        connectTheDotsRecursive(c1, mid, toRet);
    }

    @Override
    public void run() {
        // Moves the current Thread into the background
        android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_DEFAULT);

        ImageView canvas = task.getImageView();
        int[] colorsToDisplay = PaintManager.getInstance().getFrontPicture();

        int nextState = BitmapUpdateMessage.BITMAP_RENDER_COMPLETE;
        switch (task.getTask()) {
            case BitmapUpdateMessage.PENCIL_DRAW: {
                PencilUpdateMessage castedTask = (PencilUpdateMessage) task;

                // convert touch point to a pixel
                List<ScreenCord> screenCords = castedTask.getScreenCords();
                List<CanvasCord> canvasCords = new ArrayList<CanvasCord>();
                for (int i = 0; i < screenCords.size(); i++) {
                    canvasCords.add(new CanvasCord(screenCords.get(i), canvas));
                }
                if (canvasCords.size() == 2) {
                    Log.e("PaintWorker", "cord0.x,y is " + canvasCords.get(0).x + ", " + canvasCords.get(0).y);
                    Log.e("PaintWorker", "cord1.x,y is " + canvasCords.get(1).x + ", " + canvasCords.get(1).y);
                    connectTheDots/*Recursive*/(canvasCords.get(0), canvasCords.get(1), canvasCords);
                }
                else {
                    Log.e("PaintWorker","canvascords.size is off");
                }
                int thickness = castedTask.getThickness();
                int color = castedTask.getColor();
// TODO no need for canvas cords, just fill in the spots in the array
                // TODO sort points before expansion
                // measure the delta from prev point
                // only apply color to points that are relevant given the delta
                int sizeBeforeThickness = canvasCords.size();
                thickness = thickness / 2;
                Log.e("PaintWorker", "sizeBeforeThickness is " + sizeBeforeThickness);
                for (int i = 0; i < sizeBeforeThickness; i++) {
                    // TODO canvas is no longer needed i believe
                    int x = canvasCords.get(i).x;
                    int y = canvasCords.get(i).y;
                    Log.e("PaintWorker", "expanding point x,y " + x + ", " + y);
                    // wow such algo... maybe a hash????
                    for (int j = 0 - thickness; j < thickness; j++) {
                        for (int k = 0 - thickness; k < thickness; k++) {
                            int currX = x + j;
                            int currY = y + k;
                            int offset = (canvas.getWidth() * currY) + currX;
                            if (offset < colorsToDisplay.length && offset >= 0) {
                                // TODO colors
                                colorsToDisplay[offset] = color;
                            }
                        }
                    }
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
            case BitmapUpdateMessage.SAVE_DRAW:
                int[] backPicture = PaintManager.getInstance().getLocalPic(canvas);
                if (backPicture != null) {
                    Log.e("SAVEDRAW", "just got local pic");
                    for (int i = 0; i < colorsToDisplay.length; i++) {
                        if (colorsToDisplay[i] == 0) {
                            colorsToDisplay[i] = backPicture[i];
                        }
                    }
                    Log.e("SAVEDRAW", "done copying");

                    // free excessive memory
                    backPicture = null;
                    PaintManager.getInstance().clearLocalPic();
                }

                // overwrite old localpic with new localpic
                Bitmap toDisplay = Bitmap.createBitmap(colorsToDisplay, canvas.getWidth(), canvas.getHeight(), Bitmap.Config.ARGB_8888);
                FileOutputStream out = null;
                try {
                    Log.e("SAVE_DRAW", "filename is " + PaintManager.getInstance().getLocalPaintFile().getAbsolutePath());
                    Log.e("SAVE_DRAW", "can write?? " + PaintManager.getInstance().getLocalPaintFile().canWrite());
                    if (PaintManager.getInstance().getLocalPaintFile().exists()) {
                        PaintManager.getInstance().getLocalPaintFile().delete();
                    }
                    PaintManager.getInstance().getLocalPaintFile().createNewFile();

                    out = new FileOutputStream(PaintManager.getInstance().getLocalPaintFile());
                    /*ByteBuffer bb = ByteBuffer.allocate(colorsToDisplay.length * 4);
                    IntBuffer ib = bb.asIntBuffer();
                    ib.put(colorsToDisplay);
                    byte[] data = bb.array();
                    out.write(data);*/
                    toDisplay.compress(Bitmap.CompressFormat.PNG, 100, out);
                    out.close();
                } catch (Exception e) {
                    Log.e("SAVE_DRAW", "caught exception while saving " + e.getMessage());
                }
                Log.e("SAVEDRAW", "done saving");
                nextState = BitmapUpdateMessage.BITMAP_SAVE_COMPLETE;
                break;
            case BitmapUpdateMessage.CLEAR_DRAW:
                PaintManager.getInstance().clearFrontPicture(canvas);
                break;
            case BitmapUpdateMessage.INIT_DRAW:
            default:
                break;
        }

        Bitmap toDisplay = Bitmap.createBitmap(colorsToDisplay, canvas.getWidth(), canvas.getHeight(), Bitmap.Config.ARGB_8888);
        task.setBitmap(toDisplay);
        task.handleUpdateState(nextState);
    }
}
