package net.eanfang.worker.ui.adapter;

import android.graphics.drawable.Drawable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.model.OrderProgressBean;
import com.eanfang.util.GetDateUtils;
import com.github.vipulasri.timelineview.TimelineView;

import net.eanfang.worker.R;

import java.util.List;


/**
 * 订单进度的adapter
 * Created by Administrator on 2017/3/16.
 */

public class OrderProgressAdapter extends BaseQuickAdapter<OrderProgressBean, BaseViewHolder> {
    private TimelineView timelineView;
    private OrderProgressBean orderProgressBean;
    private String tempText;
    public OrderProgressAdapter(int layoutResId, List<OrderProgressBean> data) {
        super(layoutResId, data);
        orderProgressBean = data.get(data.size() - 1);
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderProgressBean item) {
        helper.setText(R.id.tv_time, item.getCreateTime().substring(11))
                .setText(R.id.tv_date, item.getCreateTime().substring(0, 10))
                .setText(R.id.tv_weeks, GetDateUtils.dateToWeek(item.getCreateTime().substring(0, 10)));
        timelineView = helper.getView(R.id.time_marker);
        Drawable marker = mContext.getResources().getDrawable(R.drawable.ic_check);
        Drawable unmarker = mContext.getResources().getDrawable(R.drawable.ic_up_backs);

        int status = item.getNodeCode();
        if (orderProgressBean.getNodeCode() == status) {
            timelineView.setMarker(marker);
        } else {
            timelineView.setMarker(unmarker);
        }
        if (item.getNodeInfo() != null) {
            tempText = "(" + item.getNodeInfo() + ")";
        } else {
            tempText = "";
        }
        switch (status) {
            case 0:
                helper.setText(R.id.tv_progress, "下单成功，等待客户支付" + tempText);
                break;
            case 1:
                helper.setText(R.id.tv_progress, "支付成功，待回电" + tempText);
                break;
            case 2:
                helper.setText(R.id.tv_progress, "电话回访成功，待上门签到" + tempText);
                break;
            case 3:
                helper.setText(R.id.tv_progress, "上门签到成功，待完工" + tempText);
                break;
            case 4:
                helper.setText(R.id.tv_progress, "完工提交，待客户确认" + tempText);
                break;
            case 5:
                helper.setText(R.id.tv_progress, " 客户确认，订单完成" + tempText);
                break;
            case 6:
                helper.setText(R.id.tv_progress, "订单已取消" + tempText);
                break;

            default:
                break;
        }
    }
}
