package com.tj.drawwithfrineds;

import android.content.Intent;
import android.graphics.Paint;
import android.support.constraint.ConstraintLayout;
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
import com.tj.drawwithfrineds.InputTool.PencilInputTool;
import com.tj.drawwithfrineds.InputTool.QuadrantInputTool;
import com.tj.drawwithfrineds.InputTool.RandomInputTool;
import com.tj.drawwithfrineds.InputTool.ViewOnlyInputTool;
import com.tj.drawwithfrineds.UpdateMessage.PencilShowView;

public class ToolSelectionActivity extends AppCompatActivity {
    private int selectedInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tool_selection);

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        myToolbar.setTitle(R.string.ToolSelectionActivityTitle);
        setSupportActionBar(myToolbar);

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);

        // set up radiogroup change handler
        selectedInput = InputTool.VIEW_ONLY;
        RadioGroup toolOptions = findViewById(R.id.toolSelectGroup);
        toolOptions.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup tools, int buttonId) {

                for (int i = 0; i < tools.getChildCount(); i++) {
                    View curr = tools.getChildAt(i);
                    if (curr instanceof RadioButton) {
                        int visibility = View.INVISIBLE;
                        boolean setInputTool = false;
                        if (((RadioButton) curr).isChecked()) {
                            visibility = View.VISIBLE;
                            setInputTool = true;
                        }
                        switch (curr.getId()) {
                            case (R.id.viewOnlyButton):
                                findViewById(R.id.viewonly_configuration).setVisibility(visibility);
                                if (setInputTool) selectedInput = InputTool.VIEW_ONLY;
                                break;
                            case (R.id.pencilButton):
                                findViewById(R.id.pencil_configuration).setVisibility(visibility);
                                if (visibility == View.VISIBLE) {
                                    ((StateSavingSeekBar)findViewById(R.id.thicknessSeekBar)).addToToTell((PencilShowView)findViewById(R.id.dotView));
                                    ((StateSavingSeekBar)findViewById(R.id.colorSeekBar)).addToToTell((PencilShowView)findViewById(R.id.dotView));
                                }
                                if (setInputTool) selectedInput = InputTool.PENCIL;
                                break;
                            case (R.id.randomButton):
                                findViewById(R.id.random_configuration).setVisibility(visibility);
                                if (setInputTool) selectedInput = InputTool.RANDOM;
                                break;
                            case (R.id.quadrantButton):
                                findViewById(R.id.quadrant_configuration).setVisibility(visibility);
                                if (setInputTool) selectedInput = InputTool.QUADRANT;
                                break;
                            default:
                                break;
                        }
                    }
                }
            }
        });
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

    private void loadConfiguredTool(Intent intent) {
        switch (selectedInput) {
            case InputTool.VIEW_ONLY: {
                ViewOnlyInputTool tool = new ViewOnlyInputTool((ConstraintLayout) findViewById(R.id.viewonly_configuration), this);
                intent.putExtra(getString(R.string.tool_type_viewonly), tool);
                break;
            }
            case InputTool.PENCIL: {
                PencilInputTool tool = new PencilInputTool((ConstraintLayout) findViewById(R.id.pencil_configuration), this);
                intent.putExtra(getString(R.string.tool_type_pencil), tool);
                break;
            }
            case InputTool.RANDOM: {
                RandomInputTool tool = new RandomInputTool((ConstraintLayout) findViewById(R.id.random_configuration), this);
                intent.putExtra(getString(R.string.tool_type_random), tool);
                break;
            }
            case InputTool.QUADRANT: {
                QuadrantInputTool tool = new QuadrantInputTool((ConstraintLayout) findViewById(R.id.quadrant_configuration), this);
                intent.putExtra(getString(R.string.tool_type_quadrant), tool);
                break;
            }
            default:
                break;
        }
    }

    @Override
    // clear all other options
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(ToolSelectionActivity.this, MainActivity.class);
        intent.putExtra(getString(R.string.painting_to_load), PaintManager.getInstance().getProjectDir().getAbsolutePath());
        switch (item.getItemId()) {
            case R.id.action_cancel:
                startActivity(intent);
                break;
            case R.id.action_save:
                intent.putExtra(getString(R.string.tool_select_intent), selectedInput);
                loadConfiguredTool(intent);
                startActivity(intent);
                break;
            default:
                break;
        }
        return true;
    }
}