package com.tj.drawwithfrineds;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;

import java.io.File;


/**
 * Created by TJ on 5/15/2018.
 */

public class PaintingButton extends AppCompatButton {
    private File paintingDir;
    public PaintingButton(Context context, File paintingDir) {
        super(context);
        this.paintingDir = paintingDir;
    }

    public File getPaintingDir() { return paintingDir; }
}
