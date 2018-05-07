package com.tj.drawwithfrineds;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;


public class NewPaintingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_painting);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        myToolbar.setTitle(R.string.NewPaintingActivityTitle);
        setSupportActionBar(myToolbar);
    }

    /**
     * the menu layout has the 'add/new' menu item
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.new_painting_action_menu, menu);
        return true;
    }

    // replace any non alphanumeric character with '_'
    /*public String getNextFileName(String paintingName) {
        StringBuffer filenameBuf = new StringBuffer(paintingName.length());
        for (int i = 0; i < paintingName.length(); i++) {
            if ((paintingName.charAt(i) >= '0' && paintingName.charAt(i) <= '9') ||
                    (paintingName.charAt(i) >= 'a' && paintingName.charAt(i) <= 'Z')) {
                filenameBuf.append(paintingName.charAt(i));
            }
            else {
                filenameBuf.append('_');
            }
        }
        return filenameBuf.toString();
    }*/

    public boolean createPaintingDir(String paintingName, File dir) {
        Log.e("createPaintingDir", "paintingName: " + paintingName + " dirname: " + dir.getName());
        FileOutputStream outputStream;
        try {
            dir.mkdir();

            // make empty global and local pics
            String globalPicName = dir.getAbsolutePath() + File.pathSeparator + "global.png";
            outputStream = openFileOutput(globalPicName, Context.MODE_PRIVATE);
            outputStream.flush();
            outputStream.close();

            String localPicName = dir.getAbsolutePath() + File.pathSeparator + "local.png";
            outputStream = openFileOutput(localPicName, Context.MODE_PRIVATE);
            outputStream.flush();
            outputStream.close();

            // make config file
            String configFileName = dir.getAbsolutePath() + File.pathSeparator + "config";
            outputStream = openFileOutput(configFileName, Context.MODE_PRIVATE);
            outputStream.write(paintingName.getBytes());
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            // TODO
            Log.e("createPaintingDir", "failed");
            return false;
        }
        return true;
    }

    public String createDirName(String paintingName) {
        StringBuilder ret = new StringBuilder("");
        for (int i = 0; i < paintingName.length(); i++) {
            if (Character.isLetter(paintingName.charAt(i))) {
                ret.append(i);
            }
            else if (paintingName.charAt(i) == ' ') {
                ret.append(' ');
            }
            else {
                Log.e("createFileName", "somehow nonvalid character!");
                return null;
            }
        }
        return ret.toString();
    }

    public File isUniquePaintingName(String paintingName) {
        String dirNameReturn = createDirName(paintingName);
        Log.e("isUniquePaintingName", "fileNameReturn is " + dirNameReturn);
        if (dirNameReturn == null) {
            return null;
        }
        File potentialPaintingDir = new File(dirNameReturn);
        if (potentialPaintingDir.exists())
            return null;
        else {
            return potentialPaintingDir;
        }
    }

    // only alphanumeric and " ", the latter will be replaced with "_"
    public boolean isValidPaintingName(String paintingName) {
        for (int i = 0; i < paintingName.length(); i++) {
            if (!(Character.isLetter(paintingName.charAt(i)) || paintingName.charAt(i) == ' ')) {
                return false;
            }
        }
        return true;
    }

    public void onFinishButtonClicked(View view) {
        EditText paintingTitleEditText = findViewById(R.id.enterPaintingTitle);
        String paintingName = paintingTitleEditText.getText().toString();
        if (!isValidPaintingName(paintingName)) {
            Toast.makeText(this, "name must be made of a-z,A-Z, or _", Toast.LENGTH_LONG).show();
        }
        File potentialPaintingDir = isUniquePaintingName(paintingName);
        if (potentialPaintingDir == null) {
            Toast.makeText(this, "A Painting Already has this Name!", Toast.LENGTH_LONG).show();
        }
        if (createPaintingDir(paintingName, potentialPaintingDir)) {
            Log.e("NewPaintingActivity", "failed to createPaintingFile");
        }
        Intent intent = new Intent(NewPaintingActivity.this, MainActivity.class);
        intent.putExtra(getString(R.string.painting_to_load), potentialPaintingDir.getName());
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_cancel:
                Intent intent = new Intent(NewPaintingActivity.this, ListPaintingsActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
        return true;
    }
}