package com.eanfang.witget.mentionedittext.edit.util;


import com.eanfang.witget.mentionedittext.edit.modle.FormatRange;
import com.eanfang.witget.mentionedittext.edit.modle.Range;

import java.util.ArrayList;
import java.util.Collections;

/**
 * @author guanluocang
 * @data 2019/4/8
 * @description
 */

public class FormatRangeManager extends RangeManager {
    public CharSequence getFormatCharSequence(String text) {
        if (isEmpty()) {
            return text;
        }

        int lastRangeTo = 0;
        ArrayList<? extends Range> ranges = get();
        Collections.sort(ranges);

        StringBuilder builder = new StringBuilder("");
        CharSequence newChar;
        for (Range range : ranges) {
            if (range instanceof FormatRange) {
                FormatRange formatRange = (FormatRange) range;
                FormatRange.FormatData convert = formatRange.getConvert();
                newChar = convert.formatCharSequence();
                builder.append(text.substring(lastRangeTo, range.getFrom()));
                builder.append(newChar);
                lastRangeTo = range.getTo();
            }
        }

        builder.append(text.substring(lastRangeTo));
        return builder.toString();
    }
}
