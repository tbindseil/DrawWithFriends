package com.tj.drawwithfrineds.UpdateMessage;

import android.widget.ImageView;

import com.tj.drawwithfrineds.Cord;

/**
 * Created by TJ on 3/11/2018.
 */

public class PencilUpdateMessage extends BitmapUpdateMessage {
    private Cord cords[];

    public PencilUpdateMessage(ImageView paintPad, int task) {
        super(paintPad, task);
    }

    public boolean setCords(int xCords[], int yCords[]) {
        if (xCords.length != yCords.length) {
            return false;
        }

        this.cords = new Cord[yCords.length];

        for (int i = 0; i < xCords.length; i++) {
            this.cords[i] = new Cord(xCords[i], yCords[i]);
        }

        return true;
    }

    public Cord[] getCords() {
        return cords;
    }
}
