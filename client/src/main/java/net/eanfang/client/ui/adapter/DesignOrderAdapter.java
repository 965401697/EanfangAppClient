package net.eanfang.client.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.config.Config;
import com.eanfang.model.DesignOrderListBean;
import com.eanfang.util.GetConstDataUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import net.eanfang.client.R;

import java.util.List;


/**
 * jorn
 * 订单列表
 */

public class DesignOrderAdapter extends BaseQuickAdapter<DesignOrderListBean.ListBean, BaseViewHolder> {

    private Config config = Config.get(mContext);
    private GetConstDataUtils constDataUtils = GetConstDataUtils.get(config);

    public DesignOrderAdapter(List<DesignOrderListBean.ListBean> data) {
        super(R.layout.item_design_list_layout, data);

    }

    @Override
    protected void convert(BaseViewHolder helper, DesignOrderListBean.ListBean item) {
        helper.setText(R.id.tv_user_name, item.getUserName());
        helper.setText(R.id.tv_order_no, "单号：" + item.getOrderNum());
        helper.setText(R.id.tv_create_time, "下单：" + item.getCreateTime());
        helper.setText(R.id.tv_business_one, "业务：" + config.getBusinessNameByCode(item.getBusinessOneCode(), 1));
        helper.setText(R.id.tv_plan_limit, "工期：" + constDataUtils.getPredictList().get(item.getPredictTime()));
        SimpleDraweeView head_pic = helper.getView(R.id.img_head);
//        if (!StringUtils.isEmpty(item.getPic1())) {
//            head_pic.setImageURI(Uri.parse(item.getPic1()));
//        }

    }

}
