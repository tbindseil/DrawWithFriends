package com.tj.drawwithfrineds.UpdateMessage;

import android.widget.ImageView;

import com.tj.drawwithfrineds.CanvasCord;

/**
 * Created by TJ on 3/11/2018.
 */

public class PencilUpdateMessage extends BitmapUpdateMessage {
    private CanvasCord canvasCords[];

    public PencilUpdateMessage(ImageView paintPad, int task) {
        super(paintPad, task);
    }

    public void setCords(CanvasCord[] cords) {
        this.canvasCords = cords;
    }

    public CanvasCord[] getCanvasCords() {
        return canvasCords;
    }
}
