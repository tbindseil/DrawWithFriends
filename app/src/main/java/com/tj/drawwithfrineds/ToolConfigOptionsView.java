package com.tj.drawwithfrineds;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by TJ on 5/19/2018.
 */

public abstract class ToolConfigOptionsView extends RelativeLayout {
    // optionSet name, name of class of optionSet, list of options<|next optionSet>
    private static String[][] options = {
            {"Initial", "ToolConfigOptionsRadio", "Draw", "Cut", "Image", "Dig"},
            {"Draw", "ToolConfigOptionsRadio", "Shape", "Pencil"},
            {"Cut", "ToolConfigOptionsRadio", "Shape", "Pencil"},
            {"Image", "ToolConfigOptionsSelect", "done"},
            {"Shape", "ToolConfigOptionsSpinner", "CornerRound"},
            {"CornerRound", "ToolConfigOptionsSlider", "Fill"},
            {"Pencil", "ToolConfigOptionsRadio", "Free|Thickness", "Straight|Thickness"},
            {"Fill", "ToolConfigOptionsRadio", "Yes|Color", "No|Thickness"},
            {"Thickness", "ToolConfigOptionsSlider", "Color"},
            {"Color", "ToolConfigOptionsColor", "Texture"},
            {"Texture", "ToolConfigOptionsRadio", "done"}
    };

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
        RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, DEFAULT_HEIGHT);
        rlp.resolveLayoutDirection(LAYOUT_DIRECTION_LTR);
        rlp.addRule(ALIGN_PARENT_START);
        navButton.setLayoutParams(rlp);
        this.addView(navButton);
    }

    public int getState() {
        return state;
    }

    public String getConfigOptionsName() {
        return configOptionsName;
    }

    public void setConfigOptionsName(String str) { configOptionsName = str; }

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

    public int getDefaultHeight() {
        return DEFAULT_HEIGHT;
    }

static boolean thingybopper = false;
    public ToolConfigOptionsView getNext() {
        String nextName = getNextName();
        for (int i = 0; i < options.length; i++) {
            if (options[i][0].equals(nextName)) {
                String nextClass = "com.tj.drawwithfrineds." + options[i][1];
                List<String> arg = new ArrayList<>();
                for (int j = 2; j < options[i].length; j++) {
                    arg.add(options[i][j]);
                }

                // crazy java stuff
                ToolConfigOptionsView ret;
                try {
                    Class<?> clazz = Class.forName(nextClass);
                    Constructor<?> constructor = clazz.getConstructor(Context.class, List.class);
                    ret = (ToolConfigOptionsView)constructor.newInstance(new Object[] {this.getContext(), arg});
                    ret.setConfigOptionsName(options[i][0]);
                } catch (Exception e) {
                    Log.e("getNext", "probably class not found");
                    for (int j = 0; j < arg.size(); j++) {
                        Log.e("getNext", "arg[" + j + "] is " + arg.get(j));
                    }
                    return null;
                }
                return ret;
            }
        }
        Log.e("getNext", "invalid nextName");
        return null;
    }

    public abstract String getNextName();

    abstract public Object getValue();
}