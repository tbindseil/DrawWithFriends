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

    private int[] currPicture;

    private Handler mHanler;
    private ExecutorService threadControl;

    // TODO instance should not require an imageview
    public static PaintManager getInstance() {
        if (instance == null) {
            instance = new PaintManager();
            return instance;
        }
        else {
            return instance;
        }
    }

    private PaintManager() {
        // start paint thread and establish messaging handlers
        mHanler  = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message message) {
                BitmapUpdateMessage update = (BitmapUpdateMessage)message.obj;
                Bitmap toDraw = update.getBitmap();
                update.getImageView().setImageBitmap(toDraw);
            }
        };

        threadControl = Executors.newFixedThreadPool(1);
    }

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

    public int[] initCurrPicture(ImageView paintPad) {
        // init picture
        if (currPicture == null) {
            currPicture = new int[paintPad.getWidth() * paintPad.getHeight()];
            for (int i = 0; i < currPicture.length; i++) {
                currPicture[i] = (int) (0x00ffffff * Math.random());
                currPicture[i] = currPicture[i] << 8;
            }
        }
        return currPicture;
    }

    public int[] getCurrPicture() {
        return currPicture;
    }
}
