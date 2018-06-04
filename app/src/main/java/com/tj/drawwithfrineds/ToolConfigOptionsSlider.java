package com.tj.drawwithfrineds;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.SeekBar;

import java.util.List;


/**
 * Created by TJ on 5/15/2018.
 */

public class ToolConfigOptionsSlider extends ToolConfigOptionsView {
    private String next;
    private SeekBar seekBar;
    private int seekPos;

    public ToolConfigOptionsSlider(Context context, List<String> next) {
        super(context);
        this.next = next.get(0);
        seekPos = 0;

        seekBar = new SeekBar(this.getContext());
        RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        rlp.addRule(RIGHT_OF, navButton.getId());
        seekBar.setLayoutParams(rlp);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                seekPos = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        this.addView(seekBar);
    }

    public ToolConfigOptionsSlider(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ToolConfigOptionsSlider(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ToolConfigOptionsSlider(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public String getNextName() {
        return next;
    }

    public int getSeekPos() { return seekPos; }
}