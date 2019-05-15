package com.eanfang.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.R;
import com.eanfang.biz.model.SecurityCompanyDetailsBean;



/**
 * @author wq
 * @data 2018/10/23
 */

public class SecurityAdapter extends BaseQuickAdapter<SecurityCompanyDetailsBean.CasesBean.ListBean, BaseViewHolder> {
    private boolean isDelete;

    public SecurityAdapter(boolean mDelete) {
        super(R.layout.layout_item_security);
        this.isDelete = mDelete;
    }

    @Override
    protected void convert(BaseViewHolder helper, SecurityCompanyDetailsBean.CasesBean.ListBean item) {
        helper.setText(R.id.tv_a,"系统类型："+item.getBusinessOneCode());
        helper.setText(R.id.tv_b,"规模："+item.getBudget());
        helper.setText(R.id.tv_c,"业务类型："+item.getBusinessOneId());
        helper.setText(R.id.tv_d,"客户："+item.getConnector());
    }

}
