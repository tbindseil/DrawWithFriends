#!/bin/bash

set -e

# make sure git status is clean
if [ "$1" != "committed" ]; then
    exit
fi

# make strings
DESCRIPTION="test"
# TODO 
DESCRIPTION_FIRST_CAP=$(echo $DESCRIPTION | awk '{print toupper($0)}')
DESCRIPTION_ALL_CAP=$(echo $DESCRIPTION | awk '{print toupper($0)}')

INPUT_TOOL_PATH=$(dirname $(find ./ -name InputTool.java))

INPUT_TOOL_SUPER="$INPUT_TOOL_PATH"
INPUT_TOOL_SUPER+="/InputTool.java"

INPUT_TOOL_SUB="$INPUT_TOOL_PATH"
INPUT_TOOL_SUB+="/"
INPUT_TOOL_SUB+="$DESCRIPTION_FIRST_CAP"
INPUT_TOOL_SUB+="InputTool.java"

# add string thing to input tool superclass
# assumes: vals go up and last one is greatest
INPUT_TOOL_VAL=$(grep "public static final int" "$INPUT_TOOL_SUPER" | tail -n 1 | cut -d " " -f 11 | cut -d ";" -f 1)
let "INPUT_TOOL_VAL=$INPUT_TOOL_VAL+1"
cat $INPUT_TOOL_SUPER | sed "/End of InputTool Vals/i public static final int $DESCRIPTION = $INPUT_TOOL_VAL;" |
        sed "s/public static final int $DESCRIPTION = $INPUT_TOOL_VAL;/    &/" > tmp
mv tmp $INPUT_TOOL_SUPER

# generate input tool subclass

echo "package com.tj.drawwithfrineds.InputTool;" > "$INPUT_TOOL_SUB"
echo "" >> "$INPUT_TOOL_SUB"
echo "import android.widget.ImageView;" >> "$INPUT_TOOL_SUB"
echo "" >> "$INPUT_TOOL_SUB"
echo "import com.tj.drawwithfrineds.InputTool.InputTool;" >> "$INPUT_TOOL_SUB"
echo "import com.tj.drawwithfrineds.UpdateMessage.BitmapUpdateMessage;" >> "$INPUT_TOOL_SUB"
echo "import com.tj.drawwithfrineds.UpdateMessage."$DESCRIPTION_FIRST_CAP"UpdateMessage;" >> "$INPUT_TOOL_SUB"
echo "" >> "$INPUT_TOOL_SUB"
echo "public class "$DESCRIPTION_FIRST_CAP"InputTool extends InputTool {" >> "$INPUT_TOOL_SUB"
echo "    @Override" >> "$INPUT_TOOL_SUB"
echo "    public BitmapUpdateMessage handleTouch(MotionEvent ev, ImageView canvas) {" >> "$INPUT_TOOL_SUB"
echo "        return NULL;" >> "$INPUT_TOOL_SUB"
echo "    }" >> "$INPUT_TOOL_SUB"
echo "}" >> "$INPUT_TOOL_SUB"

# add section to switch in mainact
STR4="case InputTool."
STR4+="$DESCRIPTION_ALL_CAP"
STR4+=":"
STR3="toolSelectButton.setText(getString(R.string."
STR3+="$DESCRIPTION"
STR3+="_tool));"
STR2="currInputTool = new "
STR2+="$DESCRIPTION_FIRST_CAP"
STR2+="InputTool();"
STR1="break;"
cat $(find ./ -name MainActivity.java) | sed "/switch (toolSelected) {/a $STR1" |
        sed "s/$STR1/                &/" |
        sed "/switch (toolSelected) {/ a $STR2" |
        sed "s/$STR2/                &/" |
        sed "/switch (toolSelected) {/ a $STR3" |
        sed "s/$STR3/                &/" |
        sed "/switch (toolSelected) {/ a $STR4" |
        sed "s/$STR4/            &/" > tmp
mv tmp $(find ./ -name MainActivity.java)

# paint worker handle update message
STR2="case BitmapUpdateMessage."
STR2+="$DESCRIPTION_ALL_CAP"
STR2+="_DRAW: {"
STR1="break;"
STR0="}"

cat $(find ./ -name PaintWorker.java) | sed "/switch (task.getTask()) {/a $STR0" |
        sed "s/$STR0/            &/" |
        sed "/switch (task.getTask()) {/a $STR1" |
        sed "s/$STR1/                &/" |
        sed "/switch (task.getTask()) {/a $STR2" |
        sed "s/$STR2/            &/" > tmp
mv tmp $(find ./ -name PaintWorker.java)

# tool selection act
STR1="return InputTool."
STR1+="$DESCRIPTION_ALL_CAP"
STR1+=":"
STR2="case (R.id."
STR2+="$DESCRIPTION"
STR2+="Button);"
cat $(find ./ -name ToolSelectionActivity.java) | sed "/switch (curr.getId()) {/a $STR1" |
        sed "s/$STR1/            &/" |
        sed "/switch (curr.getId()) {/a $STR2" |
        sed "s/$STR2/            &/" > tmp
mv tmp $(find ./ -name ToolSelectionActivity.java)

# bitmap update message int

# update message subclass

# radio button in tool select act layout

# string val
