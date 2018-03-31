package com.tj.drawwithfrineds;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.Reader;

public class ListPaintingsActivity extends AppCompatActivity {

    private void displayPaintings() {
        File[] allProjects = this.getApplicationContext().getFilesDir().listFiles();

        LinearLayout ll = findViewById(R.id.painting_list);
        BufferedReader in;
        for (int i = 0; i < allProjects.length; i++) {
            try {
                in = new BufferedReader(new FileReader(allProjects[i]));
                String currProjectTitle = in.readLine();
                TextView curr = new TextView(this.getApplicationContext());
                curr.setText(currProjectTitle);
                ll.addView(curr);
            } catch (Exception e) {
                // TODO handle exception
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_paintings);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
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
