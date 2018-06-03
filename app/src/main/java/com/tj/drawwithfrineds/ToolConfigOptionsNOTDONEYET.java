package com.tj.drawwithfrineds;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;

import java.util.List;

/**
 * Created by TJ on 5/26/2018.
 */

public class ToolConfigOptionsNOTDONEYET extends ToolConfigOptionsView {
    private EditText notDoneYetIndication;

    public ToolConfigOptionsNOTDONEYET(Context context, List<String> options) {
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
    protected void handleStateFirst() {
        super.handleStateFirst();
    }

    @Override
    protected void handleStateNotFirst() {
        super.handleStateNotFirst();
    }

    @Override
    public String getNextName() {
        return "";
    }
}