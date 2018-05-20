package com.tj.drawwithfrineds;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ScrollView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by TJ on 5/19/2018.
 */

public abstract class ToolConfigOptions extends ScrollView {
    public Map<String, String> optionToIdMap = new HashMap<>();

    public ToolConfigOptions(Context context) {
        super(context);
    }

    public ToolConfigOptions(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ToolConfigOptions(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ToolConfigOptions(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}