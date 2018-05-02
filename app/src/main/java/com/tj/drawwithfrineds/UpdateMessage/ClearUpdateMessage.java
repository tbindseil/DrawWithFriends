package com.tj.drawwithfrineds.UpdateMessage;

import android.widget.ImageView;

/**
 * Created by TJ on 4/29/2018.
 */

public class ClearUpdateMessage extends BitmapUpdateMessage {
    public ClearUpdateMessage(ImageView paintPad) {
        super(paintPad, BitmapUpdateMessage.CLEAR_DRAW);
    }
}
