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
import java.io.FileReader;
import java.util.Scanner;
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

    private int[] frontPicture;
    private int[] backPicture;
    private File projectDir;
    private File configFile;
    private File localPaintFile;
    private File globalPaintFile;

    private String projectName;
/*
    public void setConfigFile(File toSet) {
        configFile = toSet;
    }

    public File getConfigFile() {
        return configFile;
    }

    public void setLocalPaintFile(File toSet) { paintFile = toSet; }

    public File getGlobalPaintFile() { return paintFile; }
*/
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

        configFile = null;
        localPaintFile = null;
        globalPaintFile = null;

        projectName = "";

        // start paint thread and establish messaging handlers
        mHanler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message message) {
                // TODO switch on state
                int state = message.what;
                BitmapUpdateMessage update = (BitmapUpdateMessage) message.obj;
                switch (state) {
                    case BitmapUpdateMessage.BITMAP_SAVE_COMPLETE:
                        Log.e("PahandlerMessage", "about to load");
                        update.getImageView().setImageBitmap(update.getBitmap());
                        Log.e("PahandlerMessage", "about to clear");
                        clearFrontPicture(update.getImageView());
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
    }

    public void openProject(String projectDir) {
        this.projectDir = new File(projectDir);

        // TODO wait till no more events for last file
        configFile = new File(projectDir, "config");
        try {
            Scanner sr = new Scanner(configFile);
            if (sr.hasNextLine()) {
                projectName = sr.nextLine();
            }
            else {
                Log.e("openProject", "reading config file error");
            }
        } catch (Exception e) {
            Log.e("openProject", "reading config file error" + e.getMessage());
        }

        localPaintFile = new File(projectDir, "local");
        globalPaintFile = new File(projectDir,"global");
    }

    public File getProjectDir() { return projectDir; }
    public File getConfigFile() { return configFile; }
    public File getLocalPaintFile() { return localPaintFile; }
    public File getGlobalPaintFile() { return globalPaintFile; }
    public String getProjectName() { return projectName; }

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

    public void allocFrontPic(ImageView paintPad) {
        if (frontPicture == null) {
            frontPicture = new int[paintPad.getWidth() * paintPad.getHeight()];
        }
    }

    // todo, this should not depend on paintpad at all
    public int[] clearFrontPicture(ImageView paintPad) {
        Log.e("clearCurrPicture", "CLEARING PICTURE");
        // init picture
        if (frontPicture == null) {
            frontPicture = new int[paintPad.getWidth() * paintPad.getHeight()];
        }
        for (int i = 0; i < frontPicture.length; i++) {
            frontPicture[i] = 0;
        }
        return frontPicture;
    }

    public int[] getFrontPicture() { return frontPicture; }

    public int[] getLocalPic(ImageView i) {
        Log.e("getLocalPic", "calling getlocalpic");
        if (localPaintFile.exists()) {
            Bitmap toLoad = BitmapFactory.decodeFile(localPaintFile.getAbsolutePath());
            backPicture = new int[i.getHeight() * i.getWidth()];
            toLoad.getPixels(backPicture, 0, i.getWidth(), 0, 0, i.getWidth(), i.getHeight());
            return backPicture;
        }
        else {
            return null;
        }
    }

    public void clearLocalPic() { backPicture = null; }

    public int getStepMagnitude() {
        return stepMagnitude;
    }
/*
    public void loadPicture(ImageView i) {
        if (paintFile.exists()) {
            Log.e("loadPicture", "file exists");
            Bitmap toLoad = BitmapFactory.decodeFile(paintFile.getAbsolutePath());
            i.setImageBitmap(toLoad);
        }
    }*/
}