package com.tj.drawwithfrineds.UpdateMessage;

import android.widget.ImageView;

import com.tj.drawwithfrineds.ScreenCord;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TJ on 3/11/2018.
 */

public class PencilUpdateMessage extends BitmapUpdateMessage {
    private List<ScreenCord> screenCords;
    private int thickness;

    public PencilUpdateMessage(ImageView paintPad, int task, int thickness) {
        super(paintPad, task);
        this.thickness = thickness;
        screenCords = new ArrayList<ScreenCord>();
    }

    public void setCords(List<ScreenCord> cords) {
        this.screenCords = cords;
    }

    public List<ScreenCord> getScreenCords() {
        return screenCords;
    }

    public void setThickness(int thickness) {
        this.thickness = thickness;
    }

    public int getThickness() {
        return thickness;
    }
}
