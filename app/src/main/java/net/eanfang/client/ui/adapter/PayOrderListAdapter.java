package net.eanfang.client.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import net.eanfang.client.R;
import net.eanfang.client.application.EanfangApplication;
import net.eanfang.client.config.Config;
import net.eanfang.client.config.Constant;
import net.eanfang.client.ui.model.PayOrderListBean;

import java.util.List;


/**
 * 工作台--报价与支付列表的adapter
 * Created by Administrator on 2017/3/15.
 */

public class PayOrderListAdapter extends BaseQuickAdapter<PayOrderListBean.ListBean, BaseViewHolder> {
    public PayOrderListAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PayOrderListBean.ListBean item) {

//        helper.setText(R.id.tv_company_name, (item.getAssigneeCompanyOrg().getOrgName() != null ? item.getAssigneeCompanyOrg().getOrgName() : "个人客户") /*+ "(" + item.getClientname() + ")"*/);

        helper.setText(R.id.tv_order_id, "单号:" + item.getRepairOrderNum())
                .setText(R.id.tv_appointment_time, "下单:" + item.getCreateTime())
                .setText(R.id.tv_trouble_count, "项目:" + item.getProjectName())
                .setText(R.id.tv_count_money, "¥" + item.getTotalCost())
                .setText(R.id.tv_worker_name, "技师：" + item.getReportUser().getAccountEntity().getRealName());
        helper.setText(R.id.tv_state, Config.getConfig().getConstBean().getQuoteOrderConstant().get(Constant.STATUS).get(item.getStatus()));
        if (EanfangApplication.getApplication().getUser().getAccount().getDefaultUser().getCompanyEntity().getIsVerify() == 1) {
            helper.setText(R.id.tv_do_first, "联系技师");
            if (item.getStatus() == 0) {
                helper.setText(R.id.tv_do_second, "同意报价");
            } else {
                helper.setVisible(R.id.tv_do_second, false);
            }

        } else {
            helper.setText(R.id.tv_do_first, "联系技师");
            if (item.getStatus() == 0) {
                helper.setText(R.id.tv_do_second, "立即支付");
            } else {
                helper.setVisible(R.id.tv_do_second, false);
            }

        }

        helper.addOnClickListener(R.id.tv_do_first);
        helper.addOnClickListener(R.id.tv_do_second);
    }
}
