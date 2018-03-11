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
    private int[] currPicture;

    private Handler mHanler;
    private ExecutorService threadControl;

    // TODO instance should not require an imageview
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

        // init picture
        currPicture = new int[paintPad.getWidth() * paintPad.getHeight()];
        for (int i = 0; i < currPicture.length; i++) {
            currPicture[i] = (int)(0x00ffffff * Math.random());
            currPicture[i] = currPicture[i] << 8;
        }

        threadControl = Executors.newCachedThreadPool();
    }

    // TODO mutex
    public int[] getCurrPicture() {
        return currPicture;
    }

    /* the create of BitmapUpdateMessage will be done by the InputTools
    public void drawRandomButton() {
        BitmapUpdateMessage randomTask = new BitmapUpdateMessage(paintPad, BitmapUpdateMessage.RANDOM_DRAW);
        threadControl.execute(new PaintWorker(randomTask));
    }*/

    public void handleState(BitmapUpdateMessage update, int state) {
        switch (state) {
            case BitmapUpdateMessage.BITMAP_RENDER_COMPLETE:
                Message updateMessage = mHanler.obtainMessage(state, update);
                updateMessage.sendToTarget();
                break;
            case BitmapUpdateMessage.BITMAP_UPDATE_REQUEST:
                threadControl.execute(new PaintWorker(update));
                break;
            default:
                break;
        }
    }
}
