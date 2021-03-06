package com.tj.drawwithfrineds;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class ListPaintingsActivity extends AppCompatActivity {

    public void onPaintingSelected(View view) {

    }

    private void displayPaintings() {
        File[] allProjects = this.getApplicationContext().getFilesDir().listFiles();

        LinearLayout ll = (LinearLayout)findViewById(R.id.painting_list);
        BufferedReader in;
        Log.e("ListPaintingActivity", "allProjects.length is " + allProjects.length);
        for (int i = 0; i < allProjects.length; i++) {
            Log.e("ListPaintingsActivity", "i is " + i + " and allProjects[i].getNAme() is " + allProjects[i].getName());
            String currProjectTitle = allProjects[i].getName();
            Button curr = new PaintingButton(this.getApplicationContext(), allProjects[i]);
            curr.setText(currProjectTitle);
            curr.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(ListPaintingsActivity.this, MainActivity.class);
                    intent.putExtra(getString(R.string.painting_to_load), ((PaintingButton)view).getPaintingDir().getAbsolutePath());
                    startActivity(intent);
                }
            });

            ll.addView(curr);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_paintings);
        Toolbar myToolbar = (Toolbar)findViewById(R.id.my_toolbar);
        myToolbar.setTitle(R.string.ListPaintingsActivityTitle);
        setSupportActionBar(myToolbar);

        displayPaintings();
    }

    /**
     * the menu layout has the 'add/new' menu item
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.list_painting_action_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_create:
                Intent intent = new Intent(ListPaintingsActivity.this, NewPaintingActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
        return true;
    }
}