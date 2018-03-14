package com.tj.drawwithfrineds;

import android.graphics.Bitmap;
import android.widget.ImageView;

/**
 * Created by TJ on 2/25/2018.
 */
public abstract class BitmapUpdateMessage {
    private ImageView screen;
    private Bitmap bitmap;
    private int task;

    // types of tasks
    public static final int INIT_DRAW = 0;
    public static final int PENCIL_DRAW = 1;
    public static final int RANDOM_DRAW = 2;

    // status
    public static final int BITMAP_RENDER_COMPLETE = 1;
    public static final int BITMAP_UPDATE_REQUEST = 2;

    // TODO do this better
    public BitmapUpdateMessage() {
        screen = null;
        task = -1;
    }

    public BitmapUpdateMessage(ImageView paintPad, int task) {
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
                PaintManager.getInstance().handleState(this, state);
                break;
            default:
                break;
        }
    }

    public int getTask() {
        return task;
    }
}
