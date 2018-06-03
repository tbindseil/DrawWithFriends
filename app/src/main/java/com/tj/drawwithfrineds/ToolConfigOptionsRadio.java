package com.tj.drawwithfrineds;

import android.content.Context;
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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by TJ on 5/19/2018.
 */

public class ToolConfigOptionsRadio extends ToolConfigOptionsView {
    private RadioGroup rg;
    private List<RadioButton> rbs;

    public ToolConfigOptionsRadio(Context context, List<String> options) {
        super(context);
        construct(options);
    }

    public ToolConfigOptionsRadio(Context context, AttributeSet attrs, List<String> options) {
        super(context, attrs);
        construct(options);
    }

    public ToolConfigOptionsRadio(Context context, AttributeSet attrs, int defStyleAttr, List<String> options) {
        super(context, attrs, defStyleAttr);
        construct(options);
    }

    public ToolConfigOptionsRadio(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes, List<String> options) {
        super(context, attrs, defStyleAttr, defStyleRes);
        construct(options);
    }

    private void construct(List<String> options) {
        // make scrollable radio group, each option is radio button in group
        HorizontalScrollView sv = new HorizontalScrollView(this.getContext());
        rg = new RadioGroup(this.getContext());
        rg.setOrientation(LinearLayout.HORIZONTAL);
        rbs = new ArrayList<>();
        for (String op: options) {
            RadioButton rb = new RadioButton(this.getContext());
            rb.setText(op);
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
        rg.clearCheck();
    }

    @Override
    public String getNextName() {
        int checkedId = rg.getCheckedRadioButtonId();

        for (RadioButton rb: rbs) {
            if (rb.getId() == checkedId) {
                return rb.getText().toString();
            }
        }
        return "";
    }
}
