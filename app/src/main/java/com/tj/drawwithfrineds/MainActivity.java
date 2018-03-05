package com.tj.drawwithfrineds;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private PaintManager paintManager;

    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
            setSupportActionBar(myToolbar);

            paintManager = PaintManager.getInstance((ImageView)findViewById(R.id.imageView));
        }

    public void handleBitmapUpdateMessage(Message bitmapUpdate) {
        BitmapUpdateMessage message = (BitmapUpdateMessage)bitmapUpdate.obj;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.action_menu, menu);
        return true;
    }
}
