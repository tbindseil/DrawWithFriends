package com.tj.drawwithfrineds;

import android.content.Intent;
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
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private PaintManager paintManager;
    private InputTool currInputTool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        myToolbar.setTitle(R.string.MainActivityTitle);
        setSupportActionBar(myToolbar);

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);

        // display current tool in tool select button and
        // instantiate currInputTool
        Button toolSelectButton = findViewById(R.id.toolSelectButton);
        int toolSelected = getIntent().getIntExtra(getString(R.string.tool_select_intent), 0);
        switch (toolSelected) {
            case InputTool.PENCIL:
                toolSelectButton.setText(getString(R.string.pencil_tool));
                currInputTool = new PencilInputTool();
                break;
            case InputTool.VIEW_ONLY:
            default:
                toolSelectButton.setText(getString(R.string.view_only_tool));
                currInputTool = new ViewOnlyInputTool();
                break;
        }

        // setup canvas
        paintManager = PaintManager.getInstance((ImageView) findViewById(R.id.canvas));

        View canvas = findViewById(R.id.canvas);
        canvas.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                BitmapUpdateMessage update = currInputTool.handleTouch(motionEvent, (ImageView) view);
                PaintManager.getInstance((ImageView) view).handleState(update, BitmapUpdateMessage.BITMAP_UPDATE_REQUEST);
                return true;
            }
        });
    }

    public void handleBitmapUpdateMessage(Message bitmapUpdate) {
        BitmapUpdateMessage message = (BitmapUpdateMessage) bitmapUpdate.obj;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.action_menu, menu);
        return true;
    }

    public void onToolSelectButtonClick(View view) {
        Intent intent = new Intent(MainActivity.this, ToolSelectionActivity.class);
        startActivity(intent);
    }
}
