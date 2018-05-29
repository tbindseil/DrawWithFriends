package com.tj.drawwithfrineds;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;

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

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {

    }
}