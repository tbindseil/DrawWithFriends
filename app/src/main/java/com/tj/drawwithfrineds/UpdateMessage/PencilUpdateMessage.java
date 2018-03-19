package com.tj.drawwithfrineds.UpdateMessage;

import android.widget.ImageView;

import com.tj.drawwithfrineds.CanvasCord;

import java.util.List;

/**
 * Created by TJ on 3/11/2018.
 */

public class PencilUpdateMessage extends BitmapUpdateMessage {
    private List<CanvasCord> canvasCords;

    public PencilUpdateMessage(ImageView paintPad, int task) {
        super(paintPad, task);
    }

    public void setCords(List<CanvasCord> cords) {
        this.canvasCords = cords;
    }

    public List<CanvasCord> getCanvasCords() {
        return canvasCords;
    }
}
