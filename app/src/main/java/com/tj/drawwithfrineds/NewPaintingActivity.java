package com.tj.drawwithfrineds;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;


public class NewPaintingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_painting);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
    }

    /**
     * the menu layout has the 'add/new' menu item
     */
    @Override
        public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
            menuInflater.inflate(R.menu.menu_quotes_list, menu);
            super.onCreateOptionsMenu(menu, menuInflater);
        }
}
