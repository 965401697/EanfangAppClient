package com.eanfang.widget.controltool.flowlayout;

import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

/**
 * created by wtt
 * at 2019/4/25
 * summary:
 */
public interface IFlowLayout {
    void setAdapter(TagFlowLayout tagFlowLayout,TagAdapter adapter);
    void setOnTagClickListener(TagFlowLayout tagFlowLayout,IOnTagClickListener onTagClickListener);
}
