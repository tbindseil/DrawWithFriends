package com.tj.drawwithfrineds;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ScrollView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by TJ on 5/19/2018.
 */

public abstract class ToolConfigOptionsView extends ViewGroup {
    private Map<String, ToolConfigOptionsView> optionToNextMap = new HashMap<>();
    private String selectedOption = "";
    private String configOptionsName;

    protected Button navButton;

    public static final int STATE_REMOVED = 0; // not needed anymore
    public static final int STATE_FIRST = 1;
    public static final int STATE_NOT_FIRST = 2;
    private int state;


    public ToolConfigOptionsView(Context context) {
        super(context);
        construct();
    }

    public ToolConfigOptionsView(Context context, AttributeSet attrs) {
        super(context, attrs);
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
        state = STATE_REMOVED;
        navButton = new Button(this.getContext());
    }

    public int getState() { return state; }

    public String getConfigOptionsName() { return configOptionsName; }

    public void setConfigOptionsName(String name) { configOptionsName = name; }

    public String getSelectedOption() { return selectedOption; }

    public void setState(int state) {
        switch (state) {
            case STATE_REMOVED:
                handleStateRemoved();
                break;
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
    }

    protected abstract void handleStateRemoved();
    protected abstract void handleStateFirst();
    protected abstract void handleStateNotFirst();

    public abstract ToolConfigOptionsView getNext();

    public void addOptions(String option, ToolConfigOptionsView next) {
        optionToNextMap.put(option, next);
    }
}