package net.eanfang.client.ui.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.eanfang.util.GetDateUtils;

import net.eanfang.client.R;
import net.eanfang.client.ui.activity.leave_post.bean.LeavePostHistoryDayBean;

import java.text.MessageFormat;


/**
 * @author liangkailun
 * Date ：2019-06-24
 * Describe :每日报警
 */
public class LeavePostHistoryDayAdapter extends BaseQuickAdapter<LeavePostHistoryDayBean.AlertListBean, LeavePostHistoryDayAdapter.LeavePostHomeViewHolder> {

    public LeavePostHistoryDayAdapter(int rec) {
        super(rec);
    }

    @Override
    protected void convert(LeavePostHomeViewHolder helper, LeavePostHistoryDayBean.AlertListBean item) {
        if (item == null) {
            return;
        }
        if (getParentPosition(item) == 0) {
            helper.imgItemLeavePostHistoryDetailLineTop.setVisibility(View.GONE);
        } else if (getParentPosition(item) == getItemCount() - 1) {
            helper.imgItemLeavePostHistoryDetailLineBottom.setVisibility(View.GONE);
        }
        helper.tvItemLeavePostHistoryDetailName.setText(item.getAlertName());
        helper.tvItemLeavePostHistoryDetailDate.setText(MessageFormat.format("{0}-{1}", GetDateUtils.dateToHourMin(item.getLeaveTime()), GetDateUtils.dateToHourMin(item.getBackTime())));
        int hour = 60;
        helper.imgItemLeavePostHistoryDetailBar.post(() -> {
            int width = helper.imgItemLeavePostHistoryDetailBar.getWidth();
            int height = 0;
            if (item.getAbsencePeriod() < hour * 2) {
                height = width * 30 * (item.getAbsencePeriod() / 120);
                if (item.getAbsencePeriod() > hour) {
                    helper.imgItemLeavePostHistoryDetailBar.setSelected(true);
                }
            } else {
                height = width * 30;
            }
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(width, height);
            helper.imgItemLeavePostHistoryDetailBar.setLayoutParams(params);
        });
        int h = item.getAbsencePeriod() / hour;
        int min = item.getAbsencePeriod() % hour;
        helper.tvItemLeavePostHistoryDetailTime.setText(MessageFormat.format("{0}h\t{1}min", h, min));
    }

    class LeavePostHomeViewHolder extends BaseViewHolder {
        private TextView tvItemLeavePostHistoryDetailName;
        private ImageView imgItemLeavePostHistoryDetailLineTop;
        private ImageView imgItemLeavePostHistoryDetailLineBottom;
        private TextView tvItemLeavePostHistoryDetailDate;
        private ImageView imgItemLeavePostHistoryDetailBar;
        private TextView tvItemLeavePostHistoryDetailTime;

        public LeavePostHomeViewHolder(View view) {
            super(view);
            tvItemLeavePostHistoryDetailName = view.findViewById(R.id.tv_item_leave_post_history_detail_name);
            imgItemLeavePostHistoryDetailLineTop = view.findViewById(R.id.img_item_leave_post_history_detail_line_top);
            imgItemLeavePostHistoryDetailLineBottom = view.findViewById(R.id.img_item_leave_post_history_detail_line_bottom);
            tvItemLeavePostHistoryDetailDate = view.findViewById(R.id.tv_item_leave_post_history_detail_date);
            imgItemLeavePostHistoryDetailBar = view.findViewById(R.id.img_item_leave_post_history_detail_bar);
            tvItemLeavePostHistoryDetailTime = view.findViewById(R.id.tv_item_leave_post_history_detail_time);
        }
    }
}
