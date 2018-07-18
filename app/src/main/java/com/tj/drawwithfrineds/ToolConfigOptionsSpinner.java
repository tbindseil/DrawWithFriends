package com.tj.drawwithfrineds;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

/**
 * Created by TJ on 5/20/2018.
 */

public class ToolConfigOptionsSpinner extends ToolConfigOptionsView {
    private String next = "";

    TextView sides;
    private int numSides; // TODO this is not generic numNuts!@@!!!!!

    private final int BUTTON_WIDTH = 300;
    private final int TEXT_WIDTH = 200;

    public ToolConfigOptionsSpinner(Context context, List<String> next) {
        super(context);
        this.next = next.get(0);

        numSides = 3;

        Button minusButton = new Button(this.getContext());
        minusButton.setId(View.generateViewId());
        minusButton.setText("-");
        RelativeLayout.LayoutParams mblp = new RelativeLayout.LayoutParams(BUTTON_WIDTH, ToolConfigOptionsView.DEFAULT_HEIGHT);
        mblp.addRule(RIGHT_OF, navButton.getId());
        minusButton.setLayoutParams(mblp);
        minusButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (numSides > 3) {
                    numSides--;

                    sides.setText(String.valueOf(numSides));
                }
            }
        });
        addView(minusButton);

        sides = new TextView(this.getContext());
        sides.setId(View.generateViewId());
        RelativeLayout.LayoutParams slp = new RelativeLayout.LayoutParams(TEXT_WIDTH, ToolConfigOptionsView.DEFAULT_HEIGHT);
        slp.addRule(RIGHT_OF, minusButton.getId());
        sides.setLayoutParams(slp);
        addView(sides);

        Button plusButton = new Button(this.getContext());
        plusButton.setId(View.generateViewId());
        plusButton.setText("+");
        RelativeLayout.LayoutParams pblp = new RelativeLayout.LayoutParams(BUTTON_WIDTH, ToolConfigOptionsView.DEFAULT_HEIGHT);
        pblp.addRule(RIGHT_OF, sides.getId());
        plusButton.setLayoutParams(pblp);
        plusButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (numSides < 12) {
                    numSides++;

                    sides.setText(String.valueOf(numSides));
                }
            }
        });
        addView(plusButton);

        sides.setText(String.valueOf(numSides));
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

    public int getNumSides() { return numSides; }

    @Override
    public String getNextName() {
        return next;
    }

    public Object getValue() {
        return numSides;
    }
}
