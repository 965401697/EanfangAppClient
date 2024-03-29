package net.eanfang.client.ui.activity.worksapce.maintenance;

import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.biz.model.bean.OrderProgressBean;
import com.eanfang.util.DateKit;
import com.github.vipulasri.timelineview.TimelineView;

import net.eanfang.client.R;

import java.util.List;

import cn.hutool.core.date.DateUtil;

/**
 * Created by O u r on 2018/7/16.
 */

public class MaintenanceOrderStatusAdapter extends BaseQuickAdapter<OrderProgressBean, BaseViewHolder> {
    private TimelineView timelineView;
    private OrderProgressBean orderProgressBean;
    private String tempText;
    private LinearLayout mOrderFinish;

    public MaintenanceOrderStatusAdapter(int layoutResId, List<OrderProgressBean> data) {
        super(layoutResId, data);
        orderProgressBean = data.get(0);
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderProgressBean item) {
        if (!TextUtils.isEmpty(item.getCreateTime())) {
            helper.setText(R.id.tv_time, DateUtil.parse(item.getCreateTime()).toTimeStr())
                    .setText(R.id.tv_date, DateUtil.parse(item.getCreateTime()).toString("MM-dd"))
                    .setText(R.id.tv_weeks, DateKit.get(item.getCreateTime()).weekName());
        } else {
            helper.setText(R.id.tv_time, "")
                    .setText(R.id.tv_date, "")
                    .setText(R.id.tv_weeks, "");
        }
        timelineView = helper.getView(R.id.time_marker);
        mOrderFinish = helper.getView(R.id.ll_orderFinish);
        Drawable marker = mContext.getResources().getDrawable(R.drawable.ic_check_worker);
        Drawable unmarker = mContext.getResources().getDrawable(R.drawable.ic_up_backs);

        int status = item.getNodeCode();
        if (orderProgressBean.getNodeCode() == status) {
            timelineView.setMarker(marker);
        } else {
            timelineView.setMarker(unmarker);
        }
        if (item.getNodeInfo() != null) {
            tempText = item.getNodeInfo();
        } else {
            tempText = "";
        }
        switch (status) {
            case 0:
//                helper.setText(R.id.tv_progress, "待预约");
//                helper.setText(R.id.tv_progressTime, tempText);
//                break;
            case 1:
                helper.setText(R.id.tv_progress, "待回电");
                helper.setText(R.id.tv_progressTime, tempText);
                break;
            case 2:
                helper.setText(R.id.tv_progress, "待上门");
                helper.setText(R.id.tv_progressTime, tempText);
                break;
            case 3:
                helper.setText(R.id.tv_progress, "维修中");
                helper.setText(R.id.tv_progressTime, tempText);
                break;
            case 4:
                helper.setText(R.id.tv_progress, "待验收");
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
