package com.tj.drawwithfrineds;

import android.content.Context;
import android.util.AttributeSet;

public class ToolOptionsViewSlider {/*extends ToolConfigOptionsView {

    public ToolOptionsViewSlider(Context context) {
        super(context);
    }

    public ToolOptionsViewSlider(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ToolOptionsViewSlider(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ToolOptionsViewSlider(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }*/
}

/**
 * Created by TJ on 5/15/2018.
 */
 /*
public class ToolOptionsSlider extends ToolConfigOptions {
    private List<SlideUpdate> toTell = new ArrayList<>();
    private int lastSet;

    public ToolOptionsSlider(Context context) {
        super(context);
        this.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                Log.e("StateSavingSeekBar", "last set is now " + i);
                ((ToolOptionsSlider)seekBar).setLastSet(i);
                for (SlideUpdate su : toTell) {
                    if (su != null) { su.receiveSlidePosition(i, seekBar.getId()); }
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Log.e("StateSavingSeekBar", "start");
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Log.e("StateSavingSeekBar", "stop");
            }
        });
    }

    public ToolOptionsSlider(Context context, AttributeSet as) {
        super(context, as);
        this.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                Log.e("StateSavingSeekBar", "last set is now " + i);
                ((ToolOptionsSlider)seekBar).setLastSet(i);
                for (SlideUpdate su : toTell) {
                    if (su != null) { su.receiveSlidePosition(i, seekBar.getId()); }
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Log.e("StateSavingSeekBar", "start");
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Log.e("StateSavingSeekBar", "stop");
            }
        });
    }

    public ToolOptionsSlider(Context context, AttributeSet as, int i) {
        super(context, as, i);
        this.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                Log.e("StateSavingSeekBar", "last set is now " + i);
                ((ToolOptionsSlider)seekBar).setLastSet(i);
                for (SlideUpdate su : toTell) {
                    if (su != null) { su.receiveSlidePosition(i, seekBar.getId()); }
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Log.e("StateSavingSeekBar", "start");
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Log.e("StateSavingSeekBar", "stop");
            }
        });
    }

    public void addToToTell(SlideUpdate su) {
        toTell.add(su);
    }

    public int getLastSet() { return lastSet; }
    public void setLastSet(int i) { lastSet = i; }
}
*/
