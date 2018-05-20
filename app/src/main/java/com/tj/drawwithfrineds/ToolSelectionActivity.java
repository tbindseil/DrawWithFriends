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
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;

import com.tj.drawwithfrineds.InputTool.InputTool;
import com.tj.drawwithfrineds.InputTool.PencilInputTool;
import com.tj.drawwithfrineds.InputTool.QuadrantInputTool;
import com.tj.drawwithfrineds.InputTool.RandomInputTool;
import com.tj.drawwithfrineds.InputTool.ViewOnlyInputTool;
import com.tj.drawwithfrineds.UpdateMessage.PencilShowView;

public class ToolSelectionActivity extends AppCompatActivity {
    private int selectedInput;
    private ToolConfigurationView tcv;

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

        tcv = new ToolConfigurationView(this);
        ToolOptionsRadio initial = tcv.getInitialOptions();
        RadioGroup initialRadioGroup = initial.getOptions();
        initialRadioGroup.setOnCheckedChangeListener(new CurrentOptionsSelected(tcv));
        LinearLayout ll = findViewById(R.id.optionsLayout);
        ll.addView(initial);
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
    //            loadConfiguredTool(intent);
                startActivity(intent);
                break;
            default:
                break;
        }
        return true;
    }

    public void onCurrentOptionSelected(View view) {
        /*RadioButton
        String selectedOption = view.text
        ToolConfigOptions casted = (ToolConfigOptions)view;
        String nextId = casted.optionToIdMap()*/
    }
}

class CurrentOptionsSelected implements RadioGroup.OnCheckedChangeListener {
    private ToolConfigurationView tcv;

    public CurrentOptionsSelected(ToolConfigurationView tcv) {
        this.tcv = tcv;
    }

    @Override
    public void onCheckedChanged(RadioGroup rg, int i) {
        int selectedID = rg.getCheckedRadioButtonId();
        RadioButton rb = rg.findViewById(selectedID);
        String selectedOption = rb.getText().toString();

        ScrollView sv = (ScrollView)rg.getParent();
        ToolConfigOptions casted = (ToolConfigOptions)sv;
        LinearLayout ll = (LinearLayout)sv.getParent();
        View nextOptoinsSet = (View)tcv.optionInterfaces.get(casted.optionToIdMap.get(selectedOption));
        ll.addView(nextOptoinsSet);
    }
}