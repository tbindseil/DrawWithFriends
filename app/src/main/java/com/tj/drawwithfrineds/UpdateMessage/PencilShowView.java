package com.tj.drawwithfrineds.UpdateMessage;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.Log;

import com.tj.drawwithfrineds.SlideUpdate;
import com.tj.drawwithfrineds.R;

/**
 * Created by TJ on 5/19/2018.
 */

public class PencilShowView {}/*extends AppCompatImageView implements SlideUpdate {
    private int thickness = 1;
    private int color = 0xff000000;

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
        paint.setColor(color);
        canvas.drawCircle(getWidth() / 2f, getHeight() / 2f, thickness, paint);
    }

    @Override
    public void receiveSlidePosition(int pos, int from) {
        switch (from) {
            case R.id.thicknessSeekBar:
                thickness = pos;
                break;
            case R.id.colorSeekBar:
                // todo settable max
                Log.e("PencilShowView", "color is" + Integer.toHexString(color));
                float scale = pos / 100f;
                color = 0xff000000 + (int)(scale * 0x00ffffff);
                break;
        }
        invalidate();
    }
}
*/