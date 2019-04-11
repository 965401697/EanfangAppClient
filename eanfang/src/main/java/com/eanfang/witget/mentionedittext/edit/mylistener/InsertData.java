package com.eanfang.witget.mentionedittext.edit.mylistener;


import com.eanfang.witget.mentionedittext.edit.modle.FormatRange;

/**
 * @author guanluocang
 * @data 2019/4/8
 * @description
 */

public interface InsertData {
    CharSequence charSequence();

    FormatRange.FormatData formatData();

    int color();
}