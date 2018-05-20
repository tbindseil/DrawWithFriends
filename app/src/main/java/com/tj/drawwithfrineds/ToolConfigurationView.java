package com.tj.drawwithfrineds;

import android.annotation.TargetApi;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.ScrollView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by TJ on 5/19/2018.
 */
@TargetApi(24)
public class ToolConfigurationView extends View {
    public Map<String, ToolConfigOptions> optionInterfaces = new HashMap<String, ToolConfigOptions>();

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
    
    /**
     * this is where the option linking happens, its hard coded, deal with it
     */
    private void constructOptions() {
        ToolConfigOptions curr;

        // initialoptintid_select
        curr = new ToolOptionsRadio(this.getContext());
        curr.optionToIdMap.put(this.getContext().getString(R.string.opt_image), this.getContext().getString(R.string.optintid_select));
        curr.optionToIdMap.put(this.getContext().getString(R.string.opt_edit), this.getContext().getString(R.string.optintid_draworcut));
        curr.optionToIdMap.put(this.getContext().getString(R.string.opt_dig), this.getContext().getString(R.string.optintid_thickness)); // this will be like scrubbing away layers, not inmplemented yet
        optionInterfaces.put(this.getContext().getString(R.string.optintid_initial), curr);
        ToolOptionsRadio initialRadio = (ToolOptionsRadio)curr;
        initialRadio.createRadioGroupFromKeySet();

        curr = new ToolOptionsSelectImage(this.getContext());
        curr.optionToIdMap.put(this.getContext().getString(R.string.opt_any), this.getContext().getString(R.string.optintid_fin));
        optionInterfaces.put(this.getContext().getString(R.string.optintid_select), curr);

        curr = new ToolOptionsRadio(this.getContext());
        ToolConfigOptions tco = (ToolConfigOptions) curr;
        if (tco.optionToIdMap.isEmpty()) {
            Log.e("ToolConfigurationView", "contents of map are empty");
        }
        else {
            Set<String> contents = tco.optionToIdMap.keySet();
            for (String s: contents) {
                Log.e("ToolConfiguratinoView", "string is " + s);
            }
        }
        curr.optionToIdMap.clear(); // TODO why do i need this
        curr.optionToIdMap.put(this.getContext().getString(R.string.opt_draw), this.getContext().getString(R.string.optintid_shapeorpencil));
        curr.optionToIdMap.put(this.getContext().getString(R.string.opt_cut), this.getContext().getString(R.string.optintid_shapeorpencil));
        optionInterfaces.put(this.getContext().getString(R.string.optintid_draworcut), curr);
        initialRadio = (ToolOptionsRadio)curr;
        initialRadio.createRadioGroupFromKeySet();

        curr = new ToolOptionsRadio(this.getContext());
        curr.optionToIdMap.put(this.getContext().getString(R.string.opt_pencil), this.getContext().getString(R.string.optintid_freeorstraight));
        curr.optionToIdMap.put(this.getContext().getString(R.string.opt_shape), this.getContext().getString(R.string.optintid_numsides));
        optionInterfaces.put(this.getContext().getString(R.string.optintid_shapeorpencil), curr);
        initialRadio = (ToolOptionsRadio)curr;
        initialRadio.createRadioGroupFromKeySet();

        curr = new ToolOptionsRadio(this.getContext());
        curr.optionToIdMap.put(this.getContext().getString(R.string.opt_free), this.getContext().getString(R.string.optintid_thickness));
        curr.optionToIdMap.put(this.getContext().getString(R.string.opt_straight), this.getContext().getString(R.string.optintid_thickness));
        optionInterfaces.put(this.getContext().getString(R.string.optintid_freeorstraight), curr);
        initialRadio = (ToolOptionsRadio)curr;
        initialRadio.createRadioGroupFromKeySet();

        curr = new ToolOptionsSlider(this.getContext());
        curr.optionToIdMap.put(this.getContext().getString(R.string.opt_any), this.getContext().getString(R.string.optintid_color));
        optionInterfaces.put(this.getContext().getString(R.string.optintid_thickness), curr);

        curr = new ToolOptionsSpinner(this.getContext());
        curr.optionToIdMap.put(this.getContext().getString(R.string.opt_any), this.getContext().getString(R.string.optintid_cornerround));
        optionInterfaces.put(this.getContext().getString(R.string.optintid_numsides), curr);

        curr = new ToolOptionsSlider(this.getContext());
        curr.optionToIdMap.put(this.getContext().getString(R.string.opt_any), this.getContext().getString(R.string.optintid_fill));
        optionInterfaces.put(this.getContext().getString(R.string.optintid_cornerround), curr);

        curr = new ToolOptionsRadio(this.getContext());
        curr.optionToIdMap.put(this.getContext().getString(R.string.opt_yes), this.getContext().getString(R.string.optintid_color));
        curr.optionToIdMap.put(this.getContext().getString(R.string.opt_no), this.getContext().getString(R.string.optintid_thickness));
        optionInterfaces.put(this.getContext().getString(R.string.optintid_fill), curr);
        initialRadio = (ToolOptionsRadio)curr;
        initialRadio.createRadioGroupFromKeySet();

        curr = new ToolOptionsSlider(this.getContext());
        curr.optionToIdMap.put(this.getContext().getString(R.string.opt_any), this.getContext().getString(R.string.optintid_texture));
        optionInterfaces.put(this.getContext().getString(R.string.optintid_color), curr);

        curr = new ToolOptionsRadio(this.getContext());
        curr.optionToIdMap.put(this.getContext().getString(R.string.opt_any), this.getContext().getString(R.string.optintid_fin));
        optionInterfaces.put(this.getContext().getString(R.string.optintid_texture), curr);
        initialRadio = (ToolOptionsRadio)curr;
        initialRadio.createRadioGroupFromKeySet();
    }

    public ToolOptionsRadio getInitialOptions() {
        return (ToolOptionsRadio) optionInterfaces.get(this.getContext().getString(R.string.optintid_initial));
    }
}

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
