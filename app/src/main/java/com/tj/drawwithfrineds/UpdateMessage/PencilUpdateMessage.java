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
    private int color;

    public PencilUpdateMessage(ImageView paintPad, int thickness, int color) {
        super(paintPad, BitmapUpdateMessage.PENCIL_DRAW);
        this.thickness = thickness;
        this.color = color;
        screenCords = new ArrayList<>();
    }

    public void setCords(List<ScreenCord> cords) {
        this.screenCords = cords;
    }

    public List<ScreenCord> getScreenCords() {
        return screenCords;
    }

    public int getThickness() {
        return thickness;
    }

    public int getColor() {
        return color;
    }
}
