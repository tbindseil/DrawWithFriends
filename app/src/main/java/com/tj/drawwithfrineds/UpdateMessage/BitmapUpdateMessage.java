package com.tj.drawwithfrineds.UpdateMessage;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.tj.drawwithfrineds.PaintManager;

/**
 * Created by TJ on 2/25/2018.
 */
public abstract class BitmapUpdateMessage {
    // status
    public static final int BITMAP_RENDER_COMPLETE = 1;
    public static final int BITMAP_SAVE_COMPLETE = 2;
    public static final int BITMAP_UPDATE_REQUEST = 3;
    public static final int BITMAP_SAVE_REQUEST = 4;

    // Beginning of BitmapUpdateMessage Vals
    public static final int INIT_DRAW = 0;
    public static final int PENCIL_DRAW = 1;
    public static final int RANDOM_DRAW = 2;
    public static final int QUADRANT_DRAW = 3;
    public static final int SAVE_DRAW = 4;
    public static final int CLEAR_DRAW = 5;
    // End of BitmapUpdateMessage Vals

    private ImageView screen;
    private Bitmap bitmap;
    private int task;

    // todo, use some kind of rectangle offset rather than the image view itself
    public BitmapUpdateMessage(ImageView paintPad, int task) {
        screen = paintPad;
        this.task = task;
        bitmap = null;
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

    public int getTask() { return task; }

    public void handleUpdateState(int state) {
        switch (state) {
            case BITMAP_RENDER_COMPLETE:
            case BITMAP_SAVE_COMPLETE:
                PaintManager.getInstance().handleState(this, state);
                break;
            default:
                break;
        }
    }
}
