package com.tj.drawwithfrineds;

import android.content.Context;
import android.support.v7.widget.AppCompatSeekBar;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.SeekBar;

/**
 * Created by TJ on 5/15/2018.
 */

public class StateSavingSeekBar extends AppCompatSeekBar {
    private int lastSet;

    public StateSavingSeekBar(Context context) {
        super(context);
        this.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                ((StateSavingSeekBar)seekBar).setLastSet(i);
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

    public StateSavingSeekBar(Context context, AttributeSet as) {
        super(context, as);
        this.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                ((StateSavingSeekBar)seekBar).setLastSet(i);
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

    public StateSavingSeekBar(Context context, AttributeSet as, int i) {
        super(context, as, i);
        this.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                ((StateSavingSeekBar)seekBar).setLastSet(i);
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

    public int getLastSet() { return lastSet; }
    public void setLastSet(int i) { lastSet = i; }
}
