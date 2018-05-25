package com.tj.drawwithfrineds;

import android.content.Context;
import android.util.AttributeSet;
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

public abstract class ToolConfigOptionsView extends ViewGroup { // should probably just extend viewgroup this is really the best i can do today...
    public Map<String, String> optionToIdMap = new HashMap<>();
    private String selectedOption = "";
    private String configOptionsName;

    private Button navButton;
    private Button nextButton;
    private Button revertButton;

    public ToolConfigOptionsView(Context context) {
        super(context);
    }

    public ToolConfigOptionsView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ToolConfigOptionsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ToolConfigOptionsView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void construct() {
        nextButton = new Button(getContext());
        nextButton.setText("Next");
        revertButton = new Button(getContext());
        revertButton.setText("Revert");
    }

    public abstract void init();

    public String getConfigOptionsName() { return configOptionsName; }

    public void setConfigOptionsName(String name) { configOptionsName = name; }

    public String getSelectedOption() { return selectedOption; }

    public Button setFirst() {
        return (navButton = nextButton);
    }

    public Button setNotFirst() {
        return (navButton = revertButton);
    }
}