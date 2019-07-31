package com.eanfang.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.base.widget.customview.SuperTextView;
import com.eanfang.R;
import com.eanfang.biz.model.bean.SecurityCompanyDetailsBean;



/**
 * @author wq
 * @data 2018/10/23
 * @description
 */

public class GdNlAdapter extends BaseQuickAdapter<SecurityCompanyDetailsBean.AbilityListBean, BaseViewHolder> {
    private boolean isDelete;

    public GdNlAdapter(boolean mDelete) {
        super(R.layout.layout_item_more_capability);
        this.isDelete = mDelete;
    }

    @Override
    protected void convert(BaseViewHolder helper, SecurityCompanyDetailsBean.AbilityListBean item) {
        SuperTextView superTextView = helper.getView(R.id.item_stv);
        superTextView.setLeftString(item.getDataName()+"");
        superTextView.setCenterString(item.getRemark()+"");
        superTextView.setRightString(item.getUnits()+"");
    }
}
