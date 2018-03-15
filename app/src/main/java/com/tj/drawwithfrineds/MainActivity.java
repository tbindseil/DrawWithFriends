package com.tj.drawwithfrineds;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.tj.drawwithfrineds.InputTool.InputTool;
import com.tj.drawwithfrineds.InputTool.PencilInputTool;
import com.tj.drawwithfrineds.InputTool.RandomInputTool;
import com.tj.drawwithfrineds.InputTool.ViewOnlyInputTool;
import com.tj.drawwithfrineds.UpdateMessage.BitmapUpdateMessage;
import com.tj.drawwithfrineds.UpdateMessage.InitUpdateMessage;

import static java.lang.Thread.sleep;

public class MainActivity extends AppCompatActivity {
    private InputTool currInputTool;

    private int[] currPicture = null;

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
            case InputTool.RANDOM:
                // TODO put these in r/strings
                toolSelectButton.setText("Random");
                currInputTool = new RandomInputTool();
                break;
            case InputTool.VIEW_ONLY:
            default:
                toolSelectButton.setText(getString(R.string.view_only_tool));
                currInputTool = new ViewOnlyInputTool();
                break;
        }

        View canvas = findViewById(R.id.canvas);
        // TODO racecase
        canvas.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                PaintManager.getInstance();
                BitmapUpdateMessage update = currInputTool.handleTouch(null, (ImageView) view);
                if (update != null)
                    PaintManager.getInstance().handleState(update, BitmapUpdateMessage.BITMAP_UPDATE_REQUEST);
                return true;
            }
        });
    }

    public void onWindowFocusChanged(boolean hasFocus) {
        // initialize picture
        View canvas = findViewById(R.id.canvas);
        BitmapUpdateMessage init = new InitUpdateMessage((ImageView) canvas, BitmapUpdateMessage.INIT_DRAW);
        PaintManager.getInstance().handleState(init, BitmapUpdateMessage.BITMAP_UPDATE_REQUEST);
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
