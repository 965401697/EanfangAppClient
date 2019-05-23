package com.eanfang.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import com.eanfang.R;
import com.eanfang.model.SecurityCompanyDetailsBean;
import com.eanfang.widget.customview.SuperTextView;


/**
 * @author wq
 * @data 2018/10/23
 * @description
 */

public class GdNlBAdapter extends BaseQuickAdapter<SecurityCompanyDetailsBean.ToolListBean, BaseViewHolder> {
    private boolean isDelete;

    public GdNlBAdapter(boolean mDelete) {
        super(R.layout.layout_item_more_capability);
        this.isDelete = mDelete;
    }

    @Override
    protected void convert(BaseViewHolder helper, SecurityCompanyDetailsBean.ToolListBean item) {
        SuperTextView superTextView = helper.getView(R.id.item_stv);
        superTextView.setLeftString(item.getDataName()+"");
        superTextView.setCenterString(item.getRemark()+"");
        superTextView.setRightString(item.getUnits()+"");
    }
}
