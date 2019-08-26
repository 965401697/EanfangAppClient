package net.eanfang.client.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.biz.model.bean.SecurityCompanyDetailBean;
import com.eanfang.config.Config;

import net.eanfang.client.R;

/**
 * @author guanluocang
 * @data 2019/8/23
 * @description
 */
public class SecuityCompanyDetailCustomAdapter extends BaseQuickAdapter<SecurityCompanyDetailBean.CasesBean.ListBean, BaseViewHolder> {

    public SecuityCompanyDetailCustomAdapter() {
        super(R.layout.layout_companydetail_item);
    }

    @Override
    protected void convert(BaseViewHolder helper, SecurityCompanyDetailBean.CasesBean.ListBean item) {
        helper.setText(R.id.tv_business_type, "系统类型：" + Config.get().getBaseNameByCode(item.getBusinessOneCode(), 1));
        helper.setText(R.id.tv_scale, "规模：" + item.getBudget());
        helper.setText(R.id.tv_custome, "客户" + item.getConnector());
    }
}
