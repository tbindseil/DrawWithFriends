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

    // todo, check for null
    public boolean createPaintingDir(String paintingName, File dir) {
        if (dir.canWrite()){
            Log.e("canwriet", "canr");
        }
        Log.e("createPaintingDir", "paintingName: " + paintingName + " dirname: " + dir.getName());
        FileOutputStream outputStream;
        try {
            if (dir.mkdir()) {
                Log.e("true", "true");
            }
            else {
                if (dir.mkdirs()) {
                    Log.e("dirs", "dirs");
                }
                else {
                    Log.e("fuk", "fuc");
                }
            }
            Log.e("createPaintingDir", "fuck");

            // make empty global and local pics
            File globalPic = new File(dir.getAbsolutePath(), "global.png");
            globalPic.createNewFile();
            Log.e("createPaintingDir", "fuck1");
            File localPic = new File(dir.getAbsolutePath(), "local.png");
            localPic.createNewFile();
            Log.e("createPaintingDir", "fuck2");
            // make config file
            File configFile = new File(dir.getAbsolutePath(), "config");
            configFile.createNewFile();
            Log.e("createPaintingDir", "fuck3");
            outputStream = new FileOutputStream(configFile);
            outputStream.write(paintingName.getBytes());
            outputStream.flush();
            Log.e("createPaintingDir", "fuck4");
            outputStream.close();
        } catch (Exception e) {
            // TODO
            Log.e("createPaintingDir", "failed" + e.getMessage());
            return false;
        }
        return true;
    }

    public String createDirName(String paintingName) {
        StringBuilder ret = new StringBuilder("");
        for (int i = 0; i < paintingName.length(); i++) {
            if (Character.isLetter(paintingName.charAt(i))) {
                ret.append(paintingName.charAt(i));
            }
            else if (paintingName.charAt(i) == ' ') {
                ret.append('_');
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
        File potentialPaintingDir = new File(this.getApplicationContext().getFilesDir().getAbsolutePath(), dirNameReturn);
        if (potentialPaintingDir.setWritable(true, false)) {
            Log.e("setwriet", "succeeded");
        }
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
        if (!createPaintingDir(paintingName, potentialPaintingDir)) {
            Log.e("NewPaintingActivity", "failed to createPaintingFile");
        }
        Intent intent = new Intent(NewPaintingActivity.this, MainActivity.class);
        intent.putExtra(getString(R.string.painting_to_load), potentialPaintingDir.getAbsolutePath());
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