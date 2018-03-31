#!/bin/bash

set -e

# make sure git status is clean

# make strings
DESCRIPTION="test"
DESCRIPTION_FIRST_CAP=$(echo $DESCRIPTION | awk '{print toupper($0)}')

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
cat $INPUT_TOOL_SUPER | sed "/End of InputTool Vals/i public static final int $DESCRIPTION = $INPUT_TOOL_VAL;" $INPUT_TOOL_SUPER |
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

#cat InputToolTemplate | sed "s/\$DESCRIPTION_FIRST_CAP/$`echo $DESCRIPTION_FIRST_CAP`/" > "$INPUT_TOOL_SUB"

# add section to switch in mainact

# paint worker handle update message

# tool selection act

# bitmap update message int

# update message subclass

# radio button in tool select act layout

# string val
