package com.tj.drawwithfrineds;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;

/**
 * Created by TJ on 5/26/2018.
 */

public class ToolConfigOptionsNOTDONEYET extends ToolConfigOptionsView {
    private EditText notDoneYetIndication;

    public ToolConfigOptionsNOTDONEYET(Context context) {
        super(context);
        construct();
    }

    public ToolConfigOptionsNOTDONEYET(Context context, AttributeSet attrs) {
        super(context, attrs);
        construct();
    }

    public ToolConfigOptionsNOTDONEYET(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        construct();
    }

    public ToolConfigOptionsNOTDONEYET(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        construct();
    }

    private void construct() {
        notDoneYetIndication = new EditText(getContext());
        notDoneYetIndication.setText("NOT DONE YET!");
        RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        rlp.resolveLayoutDirection(LAYOUT_DIRECTION_LTR);
        Log.e("NOTDONEYET", "navButton.getId() is " + navButton.getId());
        rlp.addRule(RIGHT_OF, navButton.getId());
        notDoneYetIndication.setLayoutParams(rlp);
        this.addView(notDoneYetIndication);
    }

    @Override
    protected void handleStateRemoved() {

    }

    @Override
    protected void handleStateFirst() {

    }

    @Override
    protected void handleStateNotFirst() {

    }

    @Override
    public ToolConfigOptionsView getNext() {
        return null;
    }
}