package net.eanfang.worker.ui.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.biz.model.bean.PayOrderListBean;
import com.eanfang.util.GetConstDataUtils;

import net.eanfang.worker.R;
import net.eanfang.worker.base.WorkerApplication;


/**
 * 工作台--报价与支付列表的adapter
 * Created by Administrator on 2017/3/15.
 */

public class PayOrderListAdapter extends BaseQuickAdapter<PayOrderListBean.ListBean, BaseViewHolder> {
    public PayOrderListAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, PayOrderListBean.ListBean item) {
        // 订单是否 已读 未读 1：新订单 0 已读
        if (item.getNewOrder() == 1) {
            helper.getView(R.id.tv_order_read).setVisibility(View.VISIBLE);
        } else {
            helper.getView(R.id.tv_order_read).setVisibility(View.GONE);
        }
        helper.setText(R.id.tv_company_name, item.getOwnerCompanyOrg().getOrgName());
        helper.setText(R.id.tv_order_id, "单号:" + item.getOrderNum())
                .setText(R.id.tv_appointment_time, "下单:" + item.getCreateTime())
                .setText(R.id.tv_trouble_count, "项目:" + item.getProjectName())
                .setText(R.id.tv_count_money, "¥" + item.getTotalCost() / 100)
                .setText(R.id.tv_worker_name, "技师:" + item.getOfferer().getAccountEntity().getRealName());
        helper.setText(R.id.tv_state, GetConstDataUtils.getQuoteStatus().get(item.getStatus()));
        if (item.getCreateUserId().equals(WorkerApplication.get().getUserId())) {
            helper.setText(R.id.tv_do_first, "联系接收人");
            helper.setVisible(R.id.tv_do_first, true);
        } else if (item.getAssigneeUserId().equals(WorkerApplication.get().getUserId())) {
            helper.setText(R.id.tv_do_first, "联系报价人");
            helper.setVisible(R.id.tv_do_first, true);
            if (item.getStatus() == 0) {
                helper.setText(R.id.tv_do_second, "同意报价");
                helper.setVisible(R.id.tv_do_second, true);
            } else {
                helper.setVisible(R.id.tv_do_second, false);
            }

        } else {
            helper.setVisible(R.id.tv_do_first, false);
            helper.setVisible(R.id.tv_do_second, false);
        }

        helper.addOnClickListener(R.id.tv_do_first);
        helper.addOnClickListener(R.id.tv_do_second);
    }
}
