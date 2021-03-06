package com.tj.drawwithfrineds.InputTool;

import android.view.MotionEvent;
import android.widget.ImageView;

import com.tj.drawwithfrineds.UpdateMessage.BitmapUpdateMessage;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by TJ on 3/10/2018.
 */

public abstract class InputTool implements Serializable {

    // Beginning of InputTool Vals
    public static final int VIEW_ONLY = 0;
    public static final int PENCIL = 1;
    public static final int RANDOM = 2;
    public static final int QUADRANT = 3;
    // End of InputTool Vals

    private Map<String, Object> configurationTable;

    public InputTool(Map<String, Object> configTable) {
        configurationTable = configTable;
    }

    public abstract BitmapUpdateMessage[] handleTouch(MotionEvent ev, ImageView canvas);
}