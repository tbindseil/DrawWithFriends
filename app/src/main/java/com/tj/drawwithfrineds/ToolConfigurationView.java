package com.tj.drawwithfrineds;

import android.annotation.TargetApi;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

/**
 * Created by TJ on 5/19/2018.
 */
@TargetApi(24)
public class ToolConfigurationView {}/* extends LinearLayout {
    public Stack<ToolConfigOptionsView> configStack = new Stack<>();

    public ToolConfigurationView(Context context) {
        super(context);
        constructOptions();
    }

    public ToolConfigurationView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        constructOptions();
    }

    public ToolConfigurationView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        constructOptions();
    }

    public ToolConfigurationView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        constructOptions();
    }

    private void constructOptions() {
        setOrientation(LinearLayout.VERTICAL);

        ToolConfigOptionsView first = new ToolConfigOptionsNOTDONEYET(this.getContext());
        addView(first);



        ToolConfigOptionsView beingInitialized;

        //beingInitialized = new ToolConfigOptionsRadio(this.getContext());
        beingInitialized = new ToolConfigOptionsNOTDONEYET(this.getContext());
        beingInitialized.add
        beingInitialized.optionToIdMap.put(this.getContext().getString(R.string.opt_image), );
        beingInitialized.optionToIdMap.put(this.getContext().getString(R.string.opt_edit), this.getContext().getString(R.string.optintid_draworcut));
        beingInitialized.optionToIdMap.put(this.getContext().getString(R.string.opt_dig), this.getContext().getString(R.string.optintid_thickness)); // this will be like scrubbing away layers, not inmplemented yet
        beingInitialized.init();
        beingInitialized.setConfigOptionsName("initial");
        optionToNextConfig.put(this.getContext().getString(R.string.optintid_initial), beingInitialized);

        //beingInitialized = new ToolOptionsViewSelectImage(this.getContext());
        beingInitialized = new ToolConfigOptionsNOTDONEYET(this.getContext());
        beingInitialized.optionToIdMap.put(this.getContext().getString(R.string.opt_any), this.getContext().getString(R.string.optintid_fin));
        beingInitialized.init();
        beingInitialized.setConfigOptionsName("select");
        optionToNextConfig.put(this.getContext().getString(R.string.optintid_select), beingInitialized);

        //beingInitialized = new ToolConfigOptionsRadio(this.getContext());
        beingInitialized = new ToolConfigOptionsNOTDONEYET(this.getContext());
        beingInitialized.optionToIdMap.clear(); // TODO why do i need this
        beingInitialized.optionToIdMap.put(this.getContext().getString(R.string.opt_draw), this.getContext().getString(R.string.optintid_shapeorpencil));
        beingInitialized.optionToIdMap.put(this.getContext().getString(R.string.opt_cut), this.getContext().getString(R.string.optintid_shapeorpencil));
        beingInitialized.init();
        beingInitialized.setConfigOptionsName("draworcut");
        optionToNextConfig.put(this.getContext().getString(R.string.optintid_draworcut), beingInitialized);

        //beingInitialized = new ToolConfigOptionsRadio(this.getContext());
        beingInitialized = new ToolConfigOptionsNOTDONEYET(this.getContext());
        beingInitialized.optionToIdMap.put(this.getContext().getString(R.string.opt_pencil), this.getContext().getString(R.string.optintid_freeorstraight));
        beingInitialized.optionToIdMap.put(this.getContext().getString(R.string.opt_shape), this.getContext().getString(R.string.optintid_numsides));
        beingInitialized.init();
        beingInitialized.setConfigOptionsName("shapeorpencil");
        optionToNextConfig.put(this.getContext().getString(R.string.optintid_shapeorpencil), beingInitialized);

        //beingInitialized = new ToolConfigOptionsRadio(this.getContext());
        beingInitialized = new ToolConfigOptionsNOTDONEYET(this.getContext());
        beingInitialized.optionToIdMap.put(this.getContext().getString(R.string.opt_free), this.getContext().getString(R.string.optintid_thickness));
        beingInitialized.optionToIdMap.put(this.getContext().getString(R.string.opt_straight), this.getContext().getString(R.string.optintid_thickness));
        beingInitialized.init();
        beingInitialized.setConfigOptionsName("freeorstraight");
        optionToNextConfig.put(this.getContext().getString(R.string.optintid_freeorstraight), beingInitialized);

        //beingInitialized = new ToolOptionsViewSlider(this.getContext());
        beingInitialized = new ToolConfigOptionsNOTDONEYET(this.getContext());
        beingInitialized.optionToIdMap.put(this.getContext().getString(R.string.opt_any), this.getContext().getString(R.string.optintid_color));
        beingInitialized.init();
        beingInitialized.setConfigOptionsName("thickness");
        optionToNextConfig.put(this.getContext().getString(R.string.optintid_thickness), beingInitialized);

        //beingInitialized = new ToolOptionsViewSpinner(this.getContext());
        beingInitialized = new ToolConfigOptionsNOTDONEYET(this.getContext());
        beingInitialized.optionToIdMap.put(this.getContext().getString(R.string.opt_any), this.getContext().getString(R.string.optintid_cornerround));
        beingInitialized.init();
        beingInitialized.setConfigOptionsName("numsides");
        optionToNextConfig.put(this.getContext().getString(R.string.optintid_numsides), beingInitialized);

        //beingInitialized = new ToolOptionsViewSlider(this.getContext());
        beingInitialized = new ToolConfigOptionsNOTDONEYET(this.getContext());
        beingInitialized.optionToIdMap.put(this.getContext().getString(R.string.opt_any), this.getContext().getString(R.string.optintid_fill));
        beingInitialized.init();
        beingInitialized.setConfigOptionsName("cornerround");
        optionToNextConfig.put(this.getContext().getString(R.string.optintid_cornerround), beingInitialized);

        //beingInitialized = new ToolConfigOptionsRadio(this.getContext());
        beingInitialized = new ToolConfigOptionsNOTDONEYET(this.getContext());
        beingInitialized.optionToIdMap.put(this.getContext().getString(R.string.opt_yes), this.getContext().getString(R.string.optintid_color));
        beingInitialized.optionToIdMap.put(this.getContext().getString(R.string.opt_no), this.getContext().getString(R.string.optintid_thickness));
        beingInitialized.init();
        beingInitialized.setConfigOptionsName("fill");
        optionToNextConfig.put(this.getContext().getString(R.string.optintid_fill), beingInitialized);

        //beingInitialized = new ToolOptionsViewSlider(this.getContext());
        beingInitialized = new ToolConfigOptionsNOTDONEYET(this.getContext());
        beingInitialized.optionToIdMap.put(this.getContext().getString(R.string.opt_any), this.getContext().getString(R.string.optintid_texture));
        beingInitialized.init();
        beingInitialized.setConfigOptionsName("color");
        optionToNextConfig.put(this.getContext().getString(R.string.optintid_color), beingInitialized);

        //beingInitialized = new ToolConfigOptionsRadio(this.getContext());
        beingInitialized = new ToolConfigOptionsNOTDONEYET(this.getContext());
        beingInitialized.optionToIdMap.put(this.getContext().getString(R.string.opt_any), this.getContext().getString(R.string.optintid_fin));
        beingInitialized.init();
        beingInitialized.setConfigOptionsName("texture");
        optionToNextConfig.put(this.getContext().getString(R.string.optintid_texture), beingInitialized);
    }

    @Override
    public void onViewAdded(View child) {
        // set curr to old handler, set new view to curr, set curr to currhandler
        // this goes here ... configStack.push(curr);
        if (!(child instanceof ToolConfigOptionsView)) {
            Log.e("ToolConfigurationView", "view added thats not a ToolConfigOptionsView!");
        }

        ToolConfigOptionsView tcov = (ToolConfigOptionsView)child;

        // as long as not first options set, set the current options set handler to old handler
        if (curr != null) {
            //curr.setNotFirst().setOnClickListener(revertHandler);
            curr.setState(ToolConfigOptionsView.STATE_NOT_FIRST);
            // how the fuck am i gonna hook up this handler!!!!!!!!!!!
        }

        configStack.push(tcov);
        tcov.setFirst().setOnClickListener(nextHandler);
    }

    public void handleNext(ToolConfigOptionsView tcov) {
        addView(optionToNextConfig.get(tcov.getSelectedOption()));
    }

    public void handleRevert(ToolConfigOptionsView tcov) {

    }
}

class NavButtonHandler implements Button.OnClickListener {
    private ToolConfigurationView tcv;

    public NavButtonHandler(ToolConfigurationView tcv) {
        this.tcv = tcv;
    }

    @Override
    public void onClick(View view) {
        ViewParent parent = view.getParent();
        if (!(parent instanceof ToolConfigOptionsView)) {
            Log.e("RevertButtonHandler", "its not a ToolConfigOptionsView!");
        }
        ToolConfigOptionsView tcov = (ToolConfigOptionsView)parent;

        switch (tcov.getState()) {
            case ToolConfigOptionsView.STATE_FIRST:
                // get option selected
                // get next ToolConfigOptionsView from the current one who makes it upon click
                // place on stack
                Toast.makeText(tcv.getContext(), "not implemented yet", Toast.LENGTH_LONG).show();
                break;
            case ToolConfigOptionsView.STATE_NOT_FIRST:
                String revertTo = tcov.getConfigOptionsName();
                while (!revertTo.equals(tcv.configStack.peek())) {
                    tcv.configStack.pop();
                }
                break;
            case ToolConfigOptionsView.STATE_REMOVED:
                Log.e("NavButtonHandler", "removed button pressed somehow");
                break;
            default:
                break;
        }
    }
}*/
/** 
 * INPUT CONFIG  _call this edit, then choose draw/cut
 * image draw/cut dig - (dig would be going back to old versions)--\
 *   /     \\______                                              |
 * select   shape  pencil                                        |
 *         /           |                                         |
 *      polygon        |                                         |
 *        |            |                                         |
 *      numSides       |                                         |
 *        |            /-----\                                   |
 *      cornerRound   free   straight                            |
 *         |           |_______|                                |
 *      fill(fin cut)  |                                         |
 *      /  \           |                                         |
 *    yes  no          |                                         |
 *    /     \_        _|                                        /
 *    |        thickness --------------------------------------/
 *    |___     _/
 *        color
 *          |
 *        texture
 *
 * OUTPUT CONFIG... coming soon! spline!
 */
