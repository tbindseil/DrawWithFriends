package com.tj.drawwithfrineds;

import android.content.Intent;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import java.util.Stack;

import static android.widget.RelativeLayout.CENTER_IN_PARENT;

public class ToolSelectionActivity extends AppCompatActivity {
    public Stack<ToolConfigOptionsView> configStack = new Stack<>();
    private LinearLayout configOptionsLayout;
    private Button.OnClickListener navButtonHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tool_selection);

        Toolbar myToolbar = (Toolbar)findViewById(R.id.my_toolbar);
        myToolbar.setTitle(R.string.ToolSelectionActivityTitle);
        setSupportActionBar(myToolbar);

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);

        navButtonHandler = new Button.OnClickListener() {
            public void onClick(View view) {
                ViewParent parent = view.getParent();
                if (!(parent instanceof ToolConfigOptionsView)) {
                    Log.e("RevertButtonHandler", "its not a ToolConfigOptionsView!");
                }
                ToolConfigOptionsView tcov = (ToolConfigOptionsView)parent;

                switch (tcov.getState()) {
                    case ToolConfigOptionsView.STATE_FIRST:
                        ToolConfigOptionsView next = null;//tcov.getNext();
                        if (next == null) {
                            // TODO how to get context here Toast.makeText(super.context, "not implemented yet", Toast.LENGTH_LONG).show();
                        }
                        else {
                            configStack.peek().setState(ToolConfigOptionsView.STATE_NOT_FIRST);
                            configStack.push(next);
                            configStack.peek().setState(ToolConfigOptionsView.STATE_FIRST);
                        }
                        break;
                    case ToolConfigOptionsView.STATE_NOT_FIRST:
                        String revertTo = tcov.getConfigOptionsName();
                        while (!revertTo.equals(configStack.peek())) {
                            configStack.pop();
                        }
                        configStack.peek().setState(ToolConfigOptionsView.STATE_FIRST);
                        break;
                    case ToolConfigOptionsView.STATE_REMOVED:
                        Log.e("NavButtonHandler", "removed button pressed somehow");
                        break;
                    default:
                        break;
                }
            }
        };
        configOptionsLayout = (LinearLayout)findViewById(R.id.configLayout);
        ToolConfigOptionsView first = new ToolConfigOptionsNOTDONEYET(this);
        first.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        first.setState(ToolConfigOptionsView.STATE_FIRST);
        first.navButton.setOnClickListener(navButtonHandler);
        configStack.push(first);
        first.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 300));
        Log.e("ToolSelectionActivity", "configOptionsLayout.getId() is " + configOptionsLayout.getId());
        configOptionsLayout.addView(first);
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
                intent.putExtra(getString(R.string.tool_select_intent), 0);
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
