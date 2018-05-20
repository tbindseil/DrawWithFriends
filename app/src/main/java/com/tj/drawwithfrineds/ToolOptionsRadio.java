package com.tj.drawwithfrineds;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;

/**
 * Created by TJ on 5/19/2018.
 */

// this is basically just so i get my hashmap
public class ToolOptionsRadio extends ToolConfigOptions {
    private RadioGroup options;

    public ToolOptionsRadio(Context context) {
        super(context);
    }

    public ToolOptionsRadio(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ToolOptionsRadio(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ToolOptionsRadio(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void createRadioGroupFromKeySet() {
        options = new RadioGroup(this.getContext());
        options.setOrientation(RadioGroup.HORIZONTAL);
        this.addView(options);
        for (String opt : optionToIdMap.keySet()) {
            RadioButton optButton = new RadioButton(this.getContext());
            optButton.setText(opt);
            options.addView(optButton);
        }
    }

    public RadioGroup getOptions() { return options; }
}
