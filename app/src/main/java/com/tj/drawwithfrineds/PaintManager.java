package com.tj.drawwithfrineds;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.ImageView;

import com.tj.drawwithfrineds.UpdateMessage.BitmapUpdateMessage;

import java.io.File;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by TJ on 2/25/2018.
 */

// ideas, different threads for different sections of pic?
@TargetApi(24)
public class PaintManager {
    private int requestCount;
    private int updateCount;
    private static PaintManager instance;

    private int[] currPicture;
    private File paintFile;

    public void setFile(File toSet) {
        paintFile = toSet;
    }

    public File getFile() {
        return paintFile;
    }

    private int stepMagnitude;

    private Handler mHanler;
    private ForkJoinPool threadControl;
    private BlockingQueue<Runnable> workQueue;

    // TODO instance should not require an imageview
    public static PaintManager getInstance() {
        if (instance == null) {
            instance = new PaintManager();
            return instance;
        } else {
            return instance;
        }
    }

    private PaintManager() {
        requestCount = 0;
        updateCount = 0;
        // start paint thread and establish messaging handlers
        mHanler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message message) {
                // TODO switch on state
                int state = message.what;
                BitmapUpdateMessage update = (BitmapUpdateMessage) message.obj;
                switch (state) {
                    case BitmapUpdateMessage.BITMAP_SAVE_COMPLETE:
                        loadPicture(update.getImageView());
                        break;
                    case BitmapUpdateMessage.BITMAP_RENDER_COMPLETE:
                        Bitmap toDraw = update.getBitmap();
                        update.getImageView().setImageBitmap(toDraw);
                        break;
                    default:
                        break;
                }

            }
        };

        stepMagnitude = 1;
        workQueue = new LinkedBlockingDeque<>();
        threadControl = new ForkJoinPool(Runtime.getRuntime().availableProcessors(), ForkJoinPool.defaultForkJoinWorkerThreadFactory, null, true);
        //new ThreadPoolExecutor(1, Runtime.getRuntime().availableProcessors(), 60, TimeUnit.SECONDS, workQueue);
    }

    public void handleState(BitmapUpdateMessage update, int state) {
        switch (state) {
            case BitmapUpdateMessage.BITMAP_RENDER_COMPLETE:
            case BitmapUpdateMessage.BITMAP_SAVE_COMPLETE:
                updateCount++;
                //Log.e("PaintManager", "updateCount is " + updateCount);
                Message updateMessage = mHanler.obtainMessage(state, update);
                updateMessage.sendToTarget();
                break;
            case BitmapUpdateMessage.BITMAP_UPDATE_REQUEST:
                requestCount++;
                //Log.e("PaintManager", "requestCount is " + requestCount);
                threadControl.execute(new PaintWorker(update));
                break;
            case BitmapUpdateMessage.BITMAP_SAVE_REQUEST:
                while (threadControl.isQuiescent()) {
                    // do nothing
                }
                threadControl.execute(new PaintWorker(update));
                while (threadControl.isQuiescent()) {
                    // do nothing
                }
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
                currPicture[i] = currPicture[i] & ~0xff000000; // i think this is redundant
            }
        }
        return currPicture;
    }

    public synchronized int[] getCurrPicture() {
        return currPicture;
    }

    public void setCurrPicture(int[] picture) {
        currPicture = picture;
    }

    public int getStepMagnitude() {
        return stepMagnitude;
    }

    public void loadPicture(ImageView i) {
        if (paintFile.exists()) {
            Log.e("loadPicture", "file exists");
            Bitmap toLoad = BitmapFactory.decodeFile(paintFile.getAbsolutePath());
            i.setImageBitmap(toLoad);
        }
    }
}