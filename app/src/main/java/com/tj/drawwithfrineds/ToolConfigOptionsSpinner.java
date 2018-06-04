package com.tj.drawwithfrineds;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import java.util.List;

/**
 * Created by TJ on 5/20/2018.
 */

public class ToolConfigOptionsSpinner extends ToolConfigOptionsView {
    private String next = "";
    private Spinner spinner;

    public ToolConfigOptionsSpinner(Context context, List<String> next) {

        super(context);
        this.next = next.get(0);
        spinner = new Spinner(this.getContext());
        RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        rlp.addRule(RIGHT_OF, navButton.getId());
        // TODO, number box here with up/down arrows, spinners are dumb
        this.addView(spinner);
    }

    public ToolConfigOptionsSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ToolConfigOptionsSpinner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ToolConfigOptionsSpinner(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public String getNextName() {
        return next;
    }
}
