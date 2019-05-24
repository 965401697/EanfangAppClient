package com.eanfang.base.kit.controltool.flowlayout;

import android.view.View;

import com.zhy.view.flowlayout.FlowLayout;

/**
 * created by wtt
 * at 2019/4/25
 * summary:
 */
public interface IOnTagClickListener {
    boolean onTagClick(View view, int position, FlowLayout parent);
}
