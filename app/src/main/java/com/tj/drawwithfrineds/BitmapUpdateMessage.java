package com.tj.drawwithfrineds;

import android.graphics.Bitmap;
import android.widget.ImageView;

/**
 * Created by TJ on 2/25/2018.
 */
class BitmapUpdateMessage {
    private PaintManager paintManager;
    private ImageView screen;
    private Bitmap bitmap;
    private int task;

    // types of tasks
    public static final int RANDOM_DRAW = 0;

    // status
    public static final int BITMAP_RENDER_COMPLETE = 1;

    public BitmapUpdateMessage(ImageView paintPad, int task) {
        paintManager = PaintManager.getInstance(paintPad);
        screen = paintPad;
        this.task = task;
    }

    public void setBitmap(Bitmap bm) {
        bitmap = bm;
    }

    public ImageView getImageView() {
        return  screen;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void handleUpdateState(int state) {
        switch (state) {
            case BITMAP_RENDER_COMPLETE:
                paintManager.handleState(this, state);
                break;
            default:
                break;
        }
    }

    public int getTask() {
        return task;
    }
}
