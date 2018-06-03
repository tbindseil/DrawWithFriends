package com.tj.drawwithfrineds;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by TJ on 5/19/2018.
 */

public abstract class ToolConfigOptionsView extends RelativeLayout {
    private String configOptionsName;

    protected Button navButton;

    public static final int STATE_FIRST = 1;
    public static final int STATE_NOT_FIRST = 2;
    private int state;

    public static int DEFAULT_HEIGHT = 200;

    public ToolConfigOptionsView(Context context) {
        super(context);
        construct();
    }

    public ToolConfigOptionsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        construct();
    }

    public ToolConfigOptionsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        construct();
    }

    public ToolConfigOptionsView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        construct();
    }

    private void construct() {
        state = STATE_FIRST;
        navButton = new Button(this.getContext());
        navButton.setText("next");
        navButton.setId(View.generateViewId());
        RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        rlp.resolveLayoutDirection(LAYOUT_DIRECTION_LTR);
        rlp.addRule(ALIGN_PARENT_START);
        navButton.setLayoutParams(rlp);
        this.addView(navButton);
    }

    public int getState() { return state; }

    public String getConfigOptionsName() { return configOptionsName; }

    public void setState(int state) {
        switch (state) {
            case STATE_FIRST:
                handleStateFirst();
                break;
            case STATE_NOT_FIRST:
                handleStateNotFirst();
                break;
            default:
                Log.e("ToolConfigOptionsView", "Invalid State!");
                break;
        }
        this.requestLayout();
    }

    protected void handleStateFirst() {
        state = STATE_FIRST;
        navButton.setText("next");
    }
    protected void handleStateNotFirst() {
        state = STATE_NOT_FIRST;
        navButton.setText("revert");
    }

    public abstract ToolConfigOptionsView getNext();
}