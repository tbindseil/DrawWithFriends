#!/bin/bash

set -e

# make sure git status is clean
if [ "$1" != "committed" ]; then
    echo "must say committed so you know you could change unsaved stuff"
    exit
fi

if [ -z "$2" ]; then
    echo "usage: ./gitl.sh committed <Description>"
    exit
fi

# make strings
DESCRIPTION_FIRST_CAP="$2"
DESCRIPTION_ALL_CAP=$(echo $DESCRIPTION_FIRST_CAP | awk '{print toupper($0)}')
DESCRIPTION=$(echo $DESCRIPTION_FIRST_CAP | awk '{print tolower($0)}')

INPUT_TOOL_PATH=$(dirname $(find ./ -name InputTool.java))

INPUT_TOOL_SUPER="$INPUT_TOOL_PATH"
INPUT_TOOL_SUPER+="/InputTool.java"

INPUT_TOOL_SUB="$INPUT_TOOL_PATH"
INPUT_TOOL_SUB+="/"
INPUT_TOOL_SUB+="$DESCRIPTION_FIRST_CAP"
INPUT_TOOL_SUB+="InputTool.java"

BITMAP_UPDATE_PATH=$(dirname $(find ./ -name BitmapUpdateMessage.java))

BITMAP_UPDATE_SUPER="$BITMAP_UPDATE_PATH"
BITMAP_UPDATE_SUPER+="/BitmapUpdateMessage.java"

BITMAP_UPDATE_SUB="$BITMAP_UPDATE_PATH"
BITMAP_UPDATE_SUB+="/"
BITMAP_UPDATE_SUB+="$DESCRIPTION_FIRST_CAP"
BITMAP_UPDATE_SUB+="UpdateMessage.java"

# add string thing to input tool superclass
# assumes: vals go up and last one is greatest
INPUT_TOOL_VAL=$(grep "public static final int" "$INPUT_TOOL_SUPER" | tail -n 1 | cut -d " " -f 11 | cut -d ";" -f 1)
let "INPUT_TOOL_VAL=$INPUT_TOOL_VAL+1"
cat $INPUT_TOOL_SUPER | sed "/End of InputTool Vals/i public static final int $DESCRIPTION = $INPUT_TOOL_VAL;" |
        sed "s/public static final int $DESCRIPTION_ALL_CAP = $INPUT_TOOL_VAL;/    &/" > tmp
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
BITMAP_UPDATE_VAL=$(grep "public static final int" "$BITMAP_UPDATE_SUPER" | tail -n 1 | cut -d " " -f 11 | cut -d ";" -f 1)
let "BITMAP_UPDATE_VAL=$BITMAP_UPDATE_VAL+1"
cat $BITMAP_UPDATE_SUPER | sed "/End of BitmapUpdateMessage Vals/i public static final int $(echo $DESCRIPTION_ALL_CAP)_DRAW = $BITMAP_UPDATE_VAL;" |
        sed "s/public static final int $(echo $DESCRIPTION_ALL_CAP)_DRAW = $BITMAP_UPDATE_VAL;/    &/" > tmp
mv tmp $BITMAP_UPDATE_SUPER

# update message subclass
echo "package com.tj.drawwithfrineds.UpdateMessage;" > "$BITMAP_UPDATE_SUB"
echo "" >> "$BITMAP_UPDATE_SUB"
echo "import android.widget.ImageView;" >> "$BITMAP_UPDATE_SUB"
echo "" >> "$BITMAP_UPDATE_SUB"
echo "public class "$Description_FIRST_CAP"UpdateMessage extends BitmapUpdateMessage {" >> "$BITMAP_UPDATE_SUB"
echo "    public "$Description_FIRST_CAP"UpdateMessage(ImageView paintPad, int task) {" >> "$BITMAP_UPDATE_SUB"
echo "        super(paintPad, task);" >> "$BITMAP_UPDATE_SUB"
echo "    }" >> "$BITMAP_UPDATE_SUB"
echo "}" >> "$BITMAP_UPDATE_SUB"

# radio button in tool select act layout
STR1="<RadioButton"
STR2="android:id=\"@+id\/"
STR2+="$DESCRIPTION"
STR2+="Button\""
STR3="android:layout_width=\"match_parent\""
STR4="android:layout_height=\"wrap_content\""
STR5="android:text=\""
STR5+="$DESCRIPTION_FIRST_CAP"
STR5+="\"\/>"
cat $(find ./ -name activity_tool_selection.xml) | sed "/End Tool Radio Buttons/i $STR1" |
    sed "s/$STR1/    &/" |
    sed "/End Tool Radio Buttons/i $STR2" |
    sed "s/$STR2/    &/" |
    sed "/End Tool Radio Buttons/i $STR3" |
    sed "s/$STR3/    &/" |
    sed "/End Tool Radio Buttons/i $STR4" |
    sed "s/$STR4/    &/" |
    sed "/End Tool Radio Buttons/i $STR5" |
    sed "s/$STR5/    &/" > tmp
mv tmp $(find ./ -name activity_tool_selection.xml)

# string val
STR1="<string name=\""
STR1+="$DESCRIPTION"
STR1+="_tool\">"
STR1+="$DESCRIPTION_FIRST_CAP"
STR1+="<\/string>"
cat $(find ./ -name strings.xml) | sed "/End of Strings for tool names/i $STR1" |
        sed "s/$STR1/    &/" > tmp
mv tmp $(find ./ -name strings.xml)
