package net.eanfang.client.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import net.eanfang.client.R;
import net.eanfang.client.ui.model.OrderProgressBean;
import net.eanfang.client.util.StringUtils;

import java.util.List;


/**
 * 订单进度的adapter
 * Created by Administrator on 2017/3/16.
 */

public class OrderProgressAdapter extends BaseQuickAdapter<OrderProgressBean.AllBean, BaseViewHolder> {
    public OrderProgressAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderProgressBean.AllBean item) {
        helper.setText(R.id.tv_time, item.getTime().substring(11))
                .setText(R.id.tv_date, item.getTime().substring(0, 10));
        String status = item.getStatus();
        if (StringUtils.isEmpty(status)) {
            return;
        }
        int statu = Integer.parseInt(status);
        switch (statu) {
            case 0:
                helper.setText(R.id.tv_progress, "下单成功，等待客户支付");
                break;
            case 1:
                helper.setText(R.id.tv_progress, "报修成功，等待技师回电");
                break;
            case 2:
                helper.setText(R.id.tv_progress, "技师已回电，等待上门");
                break;
            case 3:
                helper.setText(R.id.tv_progress, "技师已上门，等待完工");
                break;
            case 4:
                helper.setText(R.id.tv_progress, "技师已提交完工，等待客户确认");
                break;
            case 5:
                helper.setText(R.id.tv_progress, "客户已确认完工，等待评价");
                break;
            case 6:
                helper.setText(R.id.tv_progress, "订单完成");
                break;
        }
    }
}
