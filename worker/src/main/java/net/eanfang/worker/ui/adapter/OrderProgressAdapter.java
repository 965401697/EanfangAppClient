package net.eanfang.worker.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.model.OrderProgressBean;

import net.eanfang.worker.R;

import java.util.List;


/**
 * 订单进度的adapter
 * Created by Administrator on 2017/3/16.
 */

public class OrderProgressAdapter extends BaseQuickAdapter<OrderProgressBean, BaseViewHolder> {
    public OrderProgressAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderProgressBean item) {
        helper.setText(R.id.tv_time, item.getCreateTime().substring(11))
                .setText(R.id.tv_date, item.getCreateTime().substring(0, 10));
//            helper.setText(R.id.tv_info,item.getNodeInfo());

        int status = item.getNodeCode();

        switch (status) {
            case 0:
                helper.setText(R.id.tv_progress, "下单成功，等待客户支付");
                break;
            case 1:
                helper.setText(R.id.tv_progress, "支付成功，待回电");
                break;
            case 2:
                helper.setText(R.id.tv_progress, "电话回访成功，待上门签到");
                break;
            case 3:
                helper.setText(R.id.tv_progress, "上门签到成功，待完工");
                break;
            case 4:
                helper.setText(R.id.tv_progress, "完工提交，待客户确认");
                break;
            case 5:
                helper.setText(R.id.tv_progress, " 客户确认，订单完成");
                break;

            default:
                break;
        }
    }
}
