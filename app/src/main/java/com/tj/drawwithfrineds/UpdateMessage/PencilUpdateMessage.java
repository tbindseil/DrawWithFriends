package com.tj.drawwithfrineds.UpdateMessage;

import android.widget.ImageView;

import com.tj.drawwithfrineds.ScreenCord;

import java.util.List;

/**
 * Created by TJ on 3/11/2018.
 */

public class PencilUpdateMessage extends BitmapUpdateMessage {
    private ScreenCord screenCord;
    private int thickness;

    public PencilUpdateMessage(ImageView paintPad, int task, int thickness) {
        super(paintPad, task);
        this.thickness = thickness;
    }

    public void setCord(ScreenCord cord) {
        this.screenCord = cord;
    }

    public ScreenCord getScreenCord() {
        return screenCord;
    }

    public void setThickness(int thickness) {
        this.thickness = thickness;
    }

    public int getThickness() {
        return thickness;
    }
}
