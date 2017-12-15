package net.eanfang.client.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;

import net.eanfang.client.R;
import net.eanfang.client.config.Config;
import net.eanfang.client.config.Constant;
import net.eanfang.client.ui.model.DesignOrderListBean;

import java.util.List;


/**
 * jorn
 * 订单列表
 */

public class DesignOrderAdapter extends BaseQuickAdapter<DesignOrderListBean.ListBean, BaseViewHolder> {

    public DesignOrderAdapter(List<DesignOrderListBean.ListBean> data) {
        super(R.layout.item_design_list_layout, data);

    }

    @Override
    protected void convert(BaseViewHolder helper, DesignOrderListBean.ListBean item) {
        helper.setText(R.id.tv_user_name, item.getUserName());
        helper.setText(R.id.tv_order_no, "单号：" + item.getOrderNum());
        helper.setText(R.id.tv_create_time, "下单：" + item.getCreateTime());
        helper.setText(R.id.tv_business_one, "业务：" + Config.getConfig().getBusinessName(item.getBusinessOneCode()));
        helper.setText(R.id.tv_plan_limit, "工期：" + Config.getConfig().getConstBean().getDesignOrderConstant().get(Constant.PREDICTTIME_TYPE).get(item.getPredictTime()));
        SimpleDraweeView head_pic = helper.getView(R.id.img_head);
//        if (!StringUtils.isEmpty(item.getPic1())) {
//            head_pic.setImageURI(Uri.parse(item.getPic1()));
//        }

    }

}
