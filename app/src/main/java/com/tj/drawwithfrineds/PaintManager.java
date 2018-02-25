package com.tj.drawwithfrineds;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.ImageView;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by TJ on 2/25/2018.
 */

// ideas, different threads for different sections of pic?

public class PaintManager {
    private static PaintManager instance;

    private ImageView paintPad;

    private Handler mHanler;
    private ExecutorService threadControl;

    public static PaintManager getInstance(ImageView paintPad) {
        if (instance == null) {
            return new PaintManager(paintPad);
        }
        else {
            return instance;
        }
    }

    private PaintManager(final ImageView paintPad) {
        this.paintPad = paintPad;

        // start paint thread and establish messaging handlers
        mHanler  = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message message) {
                BitmapUpdateMessage update = (BitmapUpdateMessage)message.obj;
                Bitmap toDraw = update.getBitmap();
                paintPad.setImageBitmap(toDraw);
            }
        };

        threadControl = Executors.newCachedThreadPool();
    }

    public void drawRandomButton() {
        BitmapUpdateMessage randomTask = new BitmapUpdateMessage(paintPad, BitmapUpdateMessage.RANDOM_DRAW);
        threadControl.execute(new PaintWorker(randomTask));
    }

    public void handleState(BitmapUpdateMessage update, int state) {
        switch (state) {
            case BitmapUpdateMessage.BITMAP_RENDER_COMPLETE:
                Message updateMessage = mHanler.obtainMessage(state, update);
                updateMessage.sendToTarget();
                break;
            default:
                break;
        }
    }
}
