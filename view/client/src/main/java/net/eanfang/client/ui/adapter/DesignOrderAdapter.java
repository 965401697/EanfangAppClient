package net.eanfang.client.ui.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.base.widget.customview.CircleImageView;
import com.eanfang.config.Config;
import com.eanfang.biz.model.DesignOrderListBean;
import com.eanfang.util.GetConstDataUtils;

import net.eanfang.client.R;


/**
 * jorn
 * 订单列表
 */

public class DesignOrderAdapter extends BaseQuickAdapter<DesignOrderListBean.ListBean, BaseViewHolder> {

    public DesignOrderAdapter() {
        super(R.layout.item_design_list_layout);

    }

    @Override
    protected void convert(BaseViewHolder helper, DesignOrderListBean.ListBean item) {
        // 订单是否 已读 未读 1：新订单 0 已读
        if (item.getNewOrder() == 1) {
            helper.getView(R.id.tv_order_read).setVisibility(View.VISIBLE);
        } else {
            helper.getView(R.id.tv_order_read).setVisibility(View.GONE);
        }
        if (item.getStatus() == 1) {
            helper.setText(R.id.tv_status, "待接单");
            helper.addOnClickListener(R.id.tv_detail);
        } else if (item.getStatus() == 2) {
            helper.setText(R.id.tv_status, "待完成");
        } else {
            helper.setText(R.id.tv_status, "已完成");
        }

        helper.setText(R.id.tv_user_name, item.getUserName());
        helper.setText(R.id.tv_order_no, "单号：" + item.getOrderNum());
        helper.setText(R.id.tv_create_time, "下单：" + item.getCreateTime());
        helper.setText(R.id.tv_business_one, "业务：" + Config.get().getBusinessNameByCode(item.getBusinessOneCode(), 1));
        helper.setText(R.id.tv_plan_limit, "工期：" + GetConstDataUtils.getPredictList().get(item.getPredictTime()));
//        CircleImageView head_pic = helper.getView(R.id.img_head);
//        if (!StringUtils.isEmpty(item.getPic1())) {
//            head_pic.setImageURI(Uri.parse(item.getPic1()));
//        }

    }

}
