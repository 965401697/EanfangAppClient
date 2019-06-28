package com.eanfang.sdk.timeline;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.R;
import com.eanfang.biz.model.OrderProgressBean;
import com.eanfang.util.GetDateUtils;
import com.eanfang.util.StringUtils;
import com.github.vipulasri.timelineview.TimelineView;

import java.util.List;

public class OrderStateAdapter extends BaseQuickAdapter<OrderProgressBean, BaseViewHolder> {
    private TimelineView timelineView;
    private String tempText;
    private LinearLayout mOrderFinish;
    private OrderProgressBean orderProgressBean;
    public OrderStateAdapter(int layoutResId, @Nullable List<OrderProgressBean> data) {
        super(layoutResId, data);
        if(data!=null&&data.size()>0) {
            orderProgressBean = data.get(0);
        }
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderProgressBean item) {
        helper.setText(R.id.tv_time, item.getCreateTime().substring(11))
                .setText(R.id.tv_date, item.getCreateTime().substring(5, 10))
                .setText(R.id.tv_weeks, GetDateUtils.dateToWeek(item.getCreateTime().substring(0, 10)));
        timelineView = helper.getView(R.id.time_marker);
        mOrderFinish = helper.getView(R.id.ll_orderFinish);
        Drawable marker = mContext.getResources().getDrawable(R.drawable.ic_check);
        Drawable unmarker = mContext.getResources().getDrawable(R.drawable.ic_up_backs);

        int status = item.getNodeCode();
        if(orderProgressBean!=null) {
            if (orderProgressBean.getNodeCode() == status) {
                timelineView.setMarker(marker);
            } else {
                timelineView.setMarker(unmarker);
            }
        }
        if (!StringUtils.isEmpty(item.getNodeInfo())) {
            tempText = "(" + item.getNodeInfo() + ")";
        } else {
            tempText = "";
        }
        switch (status) {
            case 0:
                helper.setText(R.id.tv_progress, "待支付");
                helper.setText(R.id.tv_progressTime, tempText);
                break;
            case 1:
                helper.setText(R.id.tv_progress, "待回电");
                helper.setText(R.id.tv_progressTime, tempText);
                break;
            case 2:
                helper.setText(R.id.tv_progress, "待签到");
                helper.setText(R.id.tv_progressTime, tempText);
                break;
            case 3:
                helper.setText(R.id.tv_progress, "待完工");
                helper.setText(R.id.tv_progressTime, tempText);
                break;
            case 4:
                helper.setText(R.id.tv_progress, "待确认");
                helper.setText(R.id.tv_progressTime, tempText);
                break;
            case 5:
                helper.setText(R.id.tv_progress, " 订单完成");
                helper.setText(R.id.tv_progressTime, tempText);
                mOrderFinish.setVisibility(View.VISIBLE);
                timelineView.setVisibility(View.GONE);
                break;
            case 6:
                helper.setText(R.id.tv_progress, "订单已取消");
                helper.setText(R.id.tv_progressTime, tempText);
                break;

            default:
                break;
        }
    }
}
