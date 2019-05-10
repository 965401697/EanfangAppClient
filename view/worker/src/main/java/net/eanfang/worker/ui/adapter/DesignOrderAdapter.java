package net.eanfang.worker.ui.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.config.Config;
import com.eanfang.model.DesignOrderListBean;
import com.eanfang.util.GetConstDataUtils;

import net.eanfang.worker.R;

/**
 * Created by O u r on 2018/6/25.
 */

public class DesignOrderAdapter extends BaseQuickAdapter<DesignOrderListBean.ListBean, BaseViewHolder> {
    public DesignOrderAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, DesignOrderListBean.ListBean item) {
        // 订单是否 已读 未读 1：新订单 0 已读
        if (item.getNewOrder() == 1) {
            helper.getView(R.id.tv_order_read).setVisibility(View.VISIBLE);
        } else {
            helper.getView(R.id.tv_order_read).setVisibility(View.GONE);
        }
        if (item.getCreateCompanyId() > 0) {
            helper.setText(R.id.tv_classfiy_name, "个人（" + item.getContactUser() + "）");
        } else {
            helper.setText(R.id.tv_classfiy_name, "公司（" + item.getUserName() + "）");
        }


        if (item.getStatus() == 1) {
            helper.setText(R.id.tv_status, "待接单");
            helper.addOnClickListener(R.id.tv_detail);
        } else if (item.getStatus() == 2) {
            helper.setText(R.id.tv_status, "待完成");
        } else {
            helper.setText(R.id.tv_status, "已完成");
        }

        helper.setText(R.id.tv_order_num, item.getOrderNum());
        helper.setText(R.id.tv_time, item.getCreateTime());
        helper.setText(R.id.tv_business, Config.get().getBusinessNameByCode(item.getBusinessOneCode(), 1));
        helper.setText(R.id.tv_work_time, GetConstDataUtils.getPredictList().get(item.getPredictTime()));


    }
}
