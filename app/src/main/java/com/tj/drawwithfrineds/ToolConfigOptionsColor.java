package com.tj.drawwithfrineds;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.provider.ContactsContract;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;

import java.util.List;

/**
 * Created by TJ on 7/4/2018.
 */

public class ToolConfigOptionsColor extends ToolConfigOptionsView {
    private String next;
    private SeekBar seekBarRed;
    private SeekBar seekBarGreen;
    private SeekBar seekBarBlue;
    private int seekPosRed;
    private int seekPosGreen;
    private int seekPosBlue;

    private int color;

    private ImageView sample;
    private Bitmap sampleBmp;
    private int[] sampleColors;

    public ToolConfigOptionsColor(Context context, List<String> next) {
        super(context);
        this.next = next.get(0);
        seekPosRed = 0;
        seekPosGreen = 0;
        seekPosBlue = 0;

        seekBarRed = new SeekBar(this.getContext()); seekBarRed.setMax(255);
        seekBarBlue = new SeekBar(this.getContext()); seekBarBlue.setMax(255);
        seekBarGreen = new SeekBar(this.getContext()); seekBarGreen.setMax(255);

        LinearLayout seekBarLayout = new LinearLayout(this.getContext());
        seekBarLayout.setId(View.generateViewId());
        seekBarLayout.setOrientation(LinearLayout.VERTICAL);
        RelativeLayout.LayoutParams rlp0 = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        rlp0.addRule(RIGHT_OF, navButton.getId());
        seekBarLayout.setLayoutParams(rlp0);
        this.addView(seekBarLayout);

        RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, ToolConfigOptionsView.DEFAULT_HEIGHT);
        rlp.addRule(RIGHT_OF, navButton.getId());
        seekBarRed.setLayoutParams(rlp);
        seekBarRed.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                seekPosRed = progress;
                updateSampleColor();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        seekBarLayout.addView(seekBarRed);

        RelativeLayout.LayoutParams glp = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, ToolConfigOptionsView.DEFAULT_HEIGHT);
        glp.addRule(RIGHT_OF, navButton.getId());
        glp.addRule(BELOW, seekBarRed.getId());
        seekBarGreen.setLayoutParams(glp);
        seekBarGreen.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                seekPosGreen = progress;
                updateSampleColor();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        seekBarLayout.addView(seekBarGreen);

        RelativeLayout.LayoutParams blp = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, ToolConfigOptionsView.DEFAULT_HEIGHT);
        blp.addRule(RIGHT_OF, navButton.getId());
        blp.addRule(BELOW, seekBarGreen.getId());
        seekBarBlue.setLayoutParams(blp);
        seekBarBlue.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                seekPosBlue = progress;
                updateSampleColor();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        seekBarLayout.addView(seekBarBlue);

        setBackgroundColor(0xff000000);
    }

    public ToolConfigOptionsColor(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ToolConfigOptionsColor(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ToolConfigOptionsColor(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public String getNextName() {
        return next;
    }

    public int getSeekPosRed() { return seekPosRed; }
    public int getSeekPosGreen() { return seekPosGreen; }
    public int getSeekPosBlue() { return seekPosBlue; }

    private void updateSampleColor() {
        int red = seekPosRed & 0xff;
        int green = seekPosGreen & 0xff;
        int blue = seekPosBlue & 0xff;
        color = ((int)(0xff << 24)) | ((int)(red << 16)) | ((int)(green << 8)) | ((int)(blue));
        setBackgroundColor(color);
        invalidate();
    }

    public int getDefaultHeight() {
        return 3 * DEFAULT_HEIGHT;
    }

    @Override
    protected void handleStateFirst() {
        super.handleStateFirst();
        seekBarRed.setEnabled(true);
        seekBarGreen.setEnabled(true);
        seekBarBlue.setEnabled(true);
    }

    protected void handleStateNotFirst() {
        super.handleStateNotFirst();
        seekBarRed.setEnabled(false);
        seekBarGreen.setEnabled(false);
        seekBarBlue.setEnabled(false);
    }

    public Object getValue() {
        return color;
    }
}