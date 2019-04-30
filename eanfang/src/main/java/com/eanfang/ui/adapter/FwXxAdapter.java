package com.eanfang.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.R;


/**
 * @author wq
 * @data 2018/10/23
 */

public class FwXxAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    private boolean isDelete;

    public FwXxAdapter(boolean mDelete) {
        super(R.layout.layout_item_jse);
        this.isDelete = mDelete;
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.a_tv, item+"");

    }
}
