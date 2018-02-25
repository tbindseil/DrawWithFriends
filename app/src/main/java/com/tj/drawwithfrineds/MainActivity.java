package com.tj.drawwithfrineds;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private PaintManager paintManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        paintManager = PaintManager.getInstance((ImageView)findViewById(R.id.imageView));
    }

    public void handleBitmapUpdateMessage(Message bitmapUpdate) {
        BitmapUpdateMessage message = (BitmapUpdateMessage)bitmapUpdate.obj;
    }

    public void onRandomClick(View view) {
        if (paintManager != null) {
            paintManager.drawRandomButton();
        }
    }
}