package net.eanfang.client.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.biz.model.TechnicianDetailsBean;

import net.eanfang.client.R;


/**
 * @author wq
 * @data 2018/10/23
 */

public class TechnicianDetailsfAdapter extends BaseQuickAdapter<TechnicianDetailsBean.CasesBean, BaseViewHolder> {
    private boolean isDelete;

    public TechnicianDetailsfAdapter(boolean mDelete) {
        super(R.layout.layout_item_technician_detailsf);
        this.isDelete = mDelete;
    }

    @Override
    protected void convert(BaseViewHolder helper, TechnicianDetailsBean.CasesBean item) {
        helper.setText(R.id.tv_a,"系统类型："+item.getBusinessOneName());
        helper.setText(R.id.tv_b,"规模："+item.getBudgetCost());
        helper.setText(R.id.tv_c,"业务类型："+"安装");
        helper.setText(R.id.tv_d,"客户："+item.getConnector());
    }

}
