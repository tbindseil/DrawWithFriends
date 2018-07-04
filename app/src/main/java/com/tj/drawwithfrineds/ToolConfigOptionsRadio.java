package com.tj.drawwithfrineds;

import android.content.Context;
import android.support.v7.widget.AppCompatRadioButton;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by TJ on 5/19/2018.
 */

public class ToolConfigOptionsRadio extends ToolConfigOptionsView {
    private RadioGroup rg;
    private List<ToolConfigOptionsRadioButton> rbs;

    public ToolConfigOptionsRadio(Context context, List<String> options) {
        super(context);
        construct(options);
    }

    public ToolConfigOptionsRadio(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ToolConfigOptionsRadio(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ToolConfigOptionsRadio(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void construct(List<String> options) {
        // make scrollable radio group, each option is radio button in group
        HorizontalScrollView sv = new HorizontalScrollView(this.getContext());
        rg = new RadioGroup(this.getContext());
        rg.setOrientation(LinearLayout.HORIZONTAL);
        rbs = new ArrayList<>();
        for (String op: options) {
            ToolConfigOptionsRadioButton rb = new ToolConfigOptionsRadioButton(this.getContext(), op);
            rb.setId(View.generateViewId());
            rbs.add(rb);
            rg.addView(rb);
        }
        rg.setLayoutParams(new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
        sv.addView(rg);
        RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        rlp.addRule(RIGHT_OF, navButton.getId());
        sv.setLayoutParams(rlp);
        this.addView(sv);
    }

    @Override
    protected void handleStateFirst() {
        super.handleStateFirst();
        for (View v: rbs) {
            v.setEnabled(true);
        }
    }

    protected void handleStateNotFirst() {
        super.handleStateNotFirst();
        for (View v: rbs) {
            v.setEnabled(false);
        }
    }

    @Override
    public String getNextName() {
        int checkedId = rg.getCheckedRadioButtonId();

        for (ToolConfigOptionsRadioButton rb: rbs) {
            if (rb.getId() == checkedId) {
                return rb.getNext();
            }
        }
        return "";
    }
}

class ToolConfigOptionsRadioButton extends AppCompatRadioButton {
    private String next = "";
    public ToolConfigOptionsRadioButton(Context context, String op) {
        super(context);
        if (op.contains("|")) {
            String[] tokens = op.split("\\|");
            this.setText(tokens[0]);
            this.next = tokens[1];
        }
        else {
            this.setText(op);
            this.next = op;
        }
    }

    public ToolConfigOptionsRadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ToolConfigOptionsRadioButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public String getNext() { return next; }
}