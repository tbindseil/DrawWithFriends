package com.tj.drawwithfrineds;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.ContactsContract;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tj.drawwithfrineds.InputTool.InputTool;
import com.tj.drawwithfrineds.InputTool.PencilInputTool;
import com.tj.drawwithfrineds.InputTool.QuadrantInputTool;
import com.tj.drawwithfrineds.InputTool.RandomInputTool;
import com.tj.drawwithfrineds.InputTool.ViewOnlyInputTool;
import com.tj.drawwithfrineds.UpdateMessage.BitmapUpdateMessage;
import com.tj.drawwithfrineds.UpdateMessage.ClearUpdateMessage;
import com.tj.drawwithfrineds.UpdateMessage.InitUpdateMessage;
import com.tj.drawwithfrineds.UpdateMessage.PencilUpdateMessage;
import com.tj.drawwithfrineds.UpdateMessage.QuadrantUpdateMessage;
import com.tj.drawwithfrineds.UpdateMessage.SaveUpdateMessage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;

public class MainActivity extends AppCompatActivity {
    private InputTool currInputTool;

    private String paintingTitle;

    // TODO upon relaunching of mainact from toolselectact, we must show whats on the forground of paintmanager
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // todo, if no extra, dont open project
        String paintingDirName = getIntent().getStringExtra(getString(R.string.painting_to_load));
        PaintManager.getInstance().openProject(paintingDirName);
        Log.e("MainActivity", "paintingDirName is " + paintingDirName);

        paintingTitle = PaintManager.getInstance().getProjectName();

        Toolbar myToolbar = (Toolbar)findViewById(R.id.my_toolbar);
        // TODO give title its own line or in toolbar??
        myToolbar.setTitle(paintingTitle);
        setSupportActionBar(myToolbar);

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);

        // display current tool in tool select button and
        // instantiate currInputTool
        Button toolSelectButton = (Button)findViewById(R.id.toolSelectButton);
        int toolSelected = getIntent().getIntExtra(getString(R.string.tool_select_intent), 0);
        switch (toolSelected) {
            case InputTool.PENCIL:
                toolSelectButton.setText(getString(R.string.pencil_tool));
                currInputTool = (PencilInputTool) getIntent().getSerializableExtra(getString(R.string.tool_type_pencil));
                break;
            case InputTool.RANDOM:
                // TODO put these in r/strings
                toolSelectButton.setText("Random");
                currInputTool = (RandomInputTool) getIntent().getSerializableExtra(getString(R.string.tool_type_random));
                break;
            case InputTool.QUADRANT:
                toolSelectButton.setText("Quadrant");
                currInputTool = (QuadrantInputTool) getIntent().getSerializableExtra(getString(R.string.tool_type_quadrant));
                break;
            case InputTool.VIEW_ONLY:
            default:
                // TODO
                toolSelectButton.setText(getString(R.string.view_only_tool));
                currInputTool = (ViewOnlyInputTool) getIntent().getSerializableExtra(getString(R.string.tool_type_viewonly));
                break;
        }

        View frontcanvas = findViewById(R.id.frontcanvas);
        frontcanvas.bringToFront();

        // TODO racecase
        frontcanvas.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                PaintManager.getInstance();
                BitmapUpdateMessage updates[] = currInputTool.handleTouch(motionEvent, (ImageView) view);
                if (updates != null) {
                    for (int i = 0; i < updates.length; i++) {
                        if (updates[i] != null) {
                            PaintManager.getInstance().handleState(updates[i], BitmapUpdateMessage.BITMAP_UPDATE_REQUEST);
                        }
                    }
                }
                return true;
            }
        });

        ImageView backCanvas = (ImageView)findViewById(R.id.backcanvas);
        if (PaintManager.getInstance().getLocalPaintFile().exists()) {
            backCanvas.setImageURI(Uri.fromFile(PaintManager.getInstance().getLocalPaintFile()));
        } // else it defaults to white
        //PaintManager.getInstance().loadPicture(backcanvas);
    }

    public void onWindowFocusChanged(boolean hasFocus) {
        // initialize picture
        ImageView canvas = (ImageView)findViewById(R.id.frontcanvas);
        PaintManager.getInstance().allocFrontPic(canvas); // TODO, this could probably be in a better spot
        // Log.e("onWindowFocusChanged", "filename is " + PaintManager.getInstance().getFile().getAbsolutePath());
        // BitmapUpdateMessage init = new InitUpdateMessage((ImageView) canvas);
        // PaintManager.getInstance().handleState(init, BitmapUpdateMessage.BITMAP_UPDATE_REQUEST);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.action_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_clear:
                ImageView frontCanvas = (ImageView)findViewById(R.id.frontcanvas);
                ClearUpdateMessage clearUpdateMessage = new ClearUpdateMessage(frontCanvas);
                PaintManager.getInstance().handleState(clearUpdateMessage, BitmapUpdateMessage.BITMAP_UPDATE_REQUEST);
                break;
            case R.id.action_set:
                Log.e("menuhandler", "set button pressed");
                ImageView backcanvas = (ImageView)findViewById(R.id.backcanvas);
                SaveUpdateMessage update = new SaveUpdateMessage(backcanvas);
                PaintManager.getInstance().handleState(update, BitmapUpdateMessage.BITMAP_SAVE_REQUEST);
                break;
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            default:
                break;
        }
        return true;
    }

    public void onToolSelectButtonClick(View view) {
        Intent intent = new Intent(MainActivity.this, ToolSelectionActivity.class);
        startActivity(intent);
    }
}

/*
View canvas = findViewById(R.id.canvas);
        PencilUpdateMessage test = new PencilUpdateMessage((ImageView) canvas, BitmapUpdateMessage.PENCIL_DRAW, 32);
        List<ScreenCord> testt = new ArrayList<ScreenCord>();
        testt.add(new ScreenCord((float)100.0, (float)100.0));
        testt.add(new ScreenCord((float)100.0, (float)300.0));
        test.setCords(testt);
        PaintManager.getInstance().handleState(test, BitmapUpdateMessage.BITMAP_UPDATE_REQUEST);
 */
