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
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import static android.widget.RelativeLayout.CENTER_HORIZONTAL;
import static android.widget.RelativeLayout.CENTER_IN_PARENT;

public class ToolSelectionActivity extends AppCompatActivity {
    public Stack<ToolConfigOptionsView> configStack = new Stack<>();
    private LinearLayout configOptionsLayout;
    TextView title;
    private Button.OnClickListener navButtonHandler;

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

        navButtonHandler = new Button.OnClickListener() {
            public void onClick(View view) {
                ViewParent parent = view.getParent();
                if (!(parent instanceof ToolConfigOptionsView)) {
                    Log.e("RevertButtonHandler", "its not a ToolConfigOptionsView!");
                }
                ToolConfigOptionsView tcov = (ToolConfigOptionsView) parent;

                switch (tcov.getState()) {
                    case ToolConfigOptionsView.STATE_FIRST:
                        ToolConfigOptionsView next = tcov.getNext();
                        if (next == null) {
                            // TODO how to get context here Toast.makeText(super.context, "not implemented yet", Toast.LENGTH_LONG).show();
                        } else {
                            configStack.peek().setState(ToolConfigOptionsView.STATE_NOT_FIRST);
                            addToolConfigOption(next);
                        }
                        break;
                    case ToolConfigOptionsView.STATE_NOT_FIRST:
                        while (!configStack.empty() && tcov != configStack.peek()) { //!revertTo.equals(configStack.peek())) {
                            ToolConfigOptionsView curr = configStack.pop();
                            configOptionsLayout.removeView(curr);
                        }
                        configStack.peek().setState(ToolConfigOptionsView.STATE_FIRST);
                        break;
                    default:
                        break;
                }

                setTitle(configStack.peek().getConfigOptionsName());
            }
        };
        configOptionsLayout = (LinearLayout) findViewById(R.id.configLayout);

        // add title
        title = new TextView(this);
        title.setText("Initial Options");
        configOptionsLayout.addView(title);

        List<String> initialOptions = new ArrayList<>();
        initialOptions.add("Image");
        initialOptions.add("Draw");
        initialOptions.add("Cut");
        initialOptions.add("Dig");
        ToolConfigOptionsView first = new ToolConfigOptionsRadio(this, initialOptions);
        addToolConfigOption(first);
    }

    private void addToolConfigOption(ToolConfigOptionsView tcov) {
        tcov.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        tcov.setState(ToolConfigOptionsView.STATE_FIRST);
        tcov.navButton.setOnClickListener(navButtonHandler);
        configStack.push(tcov);
        tcov.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, tcov.getDefaultHeight()));
        configOptionsLayout.addView(tcov);
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

    public void setTitle(String str) {
        title.setText(str);
        title.setGravity(CENTER_HORIZONTAL);
    }
}

/**
 + * INPUT CONFIG  _call this edit, then choose draw/cut
 + * image draw/cut dig - (dig would be going back to old versions)--\
 + *   /     \\______                                              |
 + * select   shape  pencil                                        |
 + *         /           |                                         |
 + *      polygon        |                                         |
 + *        |            |                                         |
 + *      numSides       |                                         |
 + *        |            /-----\                                   |
 + *      cornerRound   free   straight                            |
 + *         |           |_______|                                |
 + *      fill(fin cut)  |                                         |
 + *      /  \           |                                         |
 + *    yes  no          |                                         |
 + *    /     \_        _|                                        /
 + *    |        thickness --------------------------------------/
 + *    |___     _/
 + *        color
 + *          |
 + *        texture
 + *
 + * OUTPUT CONFIG... coming soon! spline!
 + */