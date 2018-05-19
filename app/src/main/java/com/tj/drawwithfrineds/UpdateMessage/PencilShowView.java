package com.tj.drawwithfrineds.UpdateMessage;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.Log;

import com.tj.drawwithfrineds.SlideUpdate;
import com.tj.drawwithfrineds.StateSavingSeekBar;

/**
 * Created by TJ on 5/19/2018.
 */

public class PencilShowView extends AppCompatImageView implements SlideUpdate {
    private int slidePos = 1;

    public PencilShowView (Context context) {
        super(context);
        setWillNotDraw(false);
    }

    public PencilShowView (Context context, AttributeSet as) {
        super(context, as);
        setWillNotDraw(false);
    }

    public PencilShowView(Context c, AttributeSet as, int i) {
        super(c, as, i);
        setWillNotDraw(false);
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.RED);
        canvas.drawCircle(getWidth() / 2f, getHeight() / 2f, slidePos, paint);
    }

    @Override
    public void receiveSlidePosition(int pos) {
        slidePos = pos;
        invalidate();
    }
}
