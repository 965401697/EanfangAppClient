package com.eanfang.base.widget.controltool.flowlayout;

import android.view.View;

import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

/**
 * created by wtt
 * at 2019/4/25
 * summary:
 */
public class FlowlayoutManager implements IFlowLayout {
    private IOnTagClickListener iOnTagClickListener;

    public void setiOnTagClickListener(IOnTagClickListener iOnTagClickListener) {
        this.iOnTagClickListener = iOnTagClickListener;
    }

    public IOnTagClickListener getiOnTagClickListener() {
        return iOnTagClickListener;
    }

    @Override
    public void setAdapter(TagFlowLayout tagFlowLayout, TagAdapter adapter) {
        tagFlowLayout.setAdapter(adapter);
    }

    @Override
    public void setOnTagClickListener(TagFlowLayout tagFlowLayout, IOnTagClickListener onTagClickListener) {
        setiOnTagClickListener(onTagClickListener);
        tagFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                if(getiOnTagClickListener()!=null) {
                    getiOnTagClickListener().onTagClick(view, position, parent);
                }
                return true;
            }
        });
    }
}
