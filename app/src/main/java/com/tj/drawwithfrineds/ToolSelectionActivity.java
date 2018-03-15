package com.tj.drawwithfrineds;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.tj.drawwithfrineds.InputTool.InputTool;

public class ToolSelectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tool_selection);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        myToolbar.setTitle(R.string.ToolSelectionActivityTitle);
        setSupportActionBar(myToolbar);

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);
    }

    /**
     * the menu layout has the 'add/new' menu item
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.tool_selection_action_menu, menu);
        return true;
    }

    // start of tool bar handler code
    public int saveCurrentTool() {
        RadioGroup tools = (RadioGroup)findViewById(R.id.toolSelectGroup);

        for (int i = 0; i < tools.getChildCount(); i++) {
            View curr = tools.getChildAt(i);
            if (curr instanceof RadioButton) {
                if (((RadioButton) curr).isChecked()){
                    switch (curr.getId()) {
                        case (R.id.viewOnlyButton):
                            return InputTool.VIEW_ONLY;
                        case (R.id.pencilButton):
                            return InputTool.PENCIL;
                        case (R.id.randomButton):
                            return InputTool.RANDOM;
                        default:
                            break;
                    }
                }
            }
        }

        return InputTool.VIEW_ONLY;
    }

    public void returnToMainActivity() {
        Intent intent = new Intent(ToolSelectionActivity.this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    // clear all other options
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(ToolSelectionActivity.this, MainActivity.class);
        switch (item.getItemId()) {
            case R.id.action_cancel:
                startActivity(intent);
                break;
            case R.id.action_save:
                int selectedInput = saveCurrentTool();
                intent.putExtra(getString(R.string.tool_select_intent), selectedInput);
                startActivity(intent);
                break;
            default:
                break;
        }
        return true;
    }
}
