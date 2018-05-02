package com.tj.drawwithfrineds.UpdateMessage;

import android.widget.ImageView;

/**
 * Created by TJ on 4/24/2018.
 */

public class SaveUpdateMessage extends BitmapUpdateMessage {
    public SaveUpdateMessage(ImageView paintPad) {
        super(paintPad, BitmapUpdateMessage.SAVE_DRAW);
    }
}