package com.tj.drawwithfrineds.UpdateMessage;

import android.widget.ImageView;

/**
 * Created by TJ on 3/13/2018.
 */

public class InitUpdateMessage extends BitmapUpdateMessage {
    public InitUpdateMessage(ImageView paintPad) {
        super(paintPad, BitmapUpdateMessage.INIT_DRAW);
    }
}
